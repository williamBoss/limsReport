package com.lims.project.sendAPP.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lims.common.constant.Constants;
import com.lims.common.utils.StringUtils;
import com.lims.common.utils.http.HttpUtils;
import com.lims.common.utils.security.Md5Utils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.domain.OriginSamplesDto;
import com.lims.project.experiment.mapper.ExperimentOrderMapper;
import com.lims.project.experiment.mapper.OriginSamplesMapper;
import com.lims.project.sendAPP.domain.SendAppSampleDTO;
import com.lims.project.sendAPP.service.SendAppService;
import com.lims.project.sendAPP.util.ServiceItemStatus;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.mapper.UploadFileMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author KING
 * @version V1.0
 * @Title: sendAppService
 * @Package com.lims.project.sendAPP.service
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/23 20:04
 */
@Service
public class SendAppServiceImpl implements SendAppService {

    private Logger log = LoggerFactory.getLogger(SendAppServiceImpl.class);

    @Autowired
    private ExperimentOrderMapper experimentOrderMapper;

    @Autowired
    private OriginSamplesMapper originSamplesMapper;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    /**
     * 向app推送报告状态
     *
     * @param experimentOrderId
     * @throws Exception
     */
    @Override
    public void sendAppInfo(Long experimentOrderId) throws Exception {
        //随机码 code
        String code = Constants.CODE;
        //秘钥
        String signKey = RuoYiConfig.getAppSignKey();
        //为时间戳，精确到秒
        Long ts = System.currentTimeMillis();
        //组装json数据
        ExperimentOrderVo orderInfo = experimentOrderMapper.selectExperimentOrderById(experimentOrderId);
        Map<String, Object> data = new HashMap<>();
        data.put("orderCode", orderInfo.getOrderCode());
        data.put("reportStatus", ServiceItemStatus.STATUS_REPORT_GENERATE);
        //获得时间戳
        data.put("timeComplete", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(orderInfo.getUpdateTime()).getTime());
        List<OriginSamplesDto> sampleInfo = originSamplesMapper.selectOriginSamples(orderInfo.getExperimentCode());
        List<SendAppSampleDTO> sendAppSampleList = new ArrayList<>();
        for (OriginSamplesDto originSamplesDto : sampleInfo) {
            SendAppSampleDTO sendAppSampleDTO = new SendAppSampleDTO();
            sendAppSampleDTO.setSampleCode(originSamplesDto.getSampleCode());
            if (originSamplesDto.getSampleStatus().equals("1")) {
                sendAppSampleDTO.setQualified(true);
            } else {
                sendAppSampleDTO.setQualified(false);
            }
            sendAppSampleList.add(sendAppSampleDTO);
        }
        String sampleJson = JSON.toJSONString(sendAppSampleList);
        data.put("sampleList", sampleJson);
        String body = JSONObject.toJSONString(data);
        //签名 sign
        String sign = Md5Utils.hash(signKey + ts + code + body.trim()).toUpperCase();
        String param = "ts=" + ts + "" + "&code=" + code + "&sign=" + sign;
        String res = HttpUtils.doPostBodyData(RuoYiConfig.getAppSendUrl() + param, body);
        System.out.println(res);
    }

    /**
     * 获取文件
     *
     * @param response
     * @param orderCode
     * @return
     * @throws IOException
     */
    @Override
    public ResponseEntity<FileSystemResource> getReportFile(HttpServletResponse response, String orderCode)
        throws IOException {
        ExperimentOrderVo experimentOrderVo = experimentOrderMapper.selectExperimentOrderByOrderCode(orderCode);
        if (experimentOrderVo == null) {
            response.sendError(403, "请求参数异常");
        } else {
            List<UploadFilePO> listFiles = uploadFileMapper
                .selectByPrimarOrderId(experimentOrderVo.getExperimentOrderId());
            if (listFiles.size() > 0) {
                UploadFilePO file = listFiles.get(0);
                String path = file.getSavePath();
                int dirLastIndex = Constants.RESOURCE_PREFIX.length() + 1;
                String currentDir = StringUtils.substring(path, dirLastIndex);
                String filePath = RuoYiConfig.getProfile() + "/" + currentDir;
                String suffix = path.substring(path.lastIndexOf(".") + 1);
                Map<String, String> params = new HashMap<>();
                if (suffix.equals("zip")) {
                    params.put("suffix", "1");
                } else {
                    params.put("suffix", "0");
                }
                return export(new File(filePath), file.getOriginFileName(), params);
            } else {
                response.sendError(403, "无法请求该报告");
            }
        }
        return null;
    }

    public static ResponseEntity<FileSystemResource> export(File file, String fileName, Map<String, String> params) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + fileName
            .substring(fileName.indexOf("."), fileName.length()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        params.forEach(headers::add);
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

    /**
     * 发送多文件
     *
     * @param url
     * @param files
     * @param textBody
     * @param header
     * @return
     */
    @Override
    public String doUpload(String url, Map<String, File> files, Map<String, Object> textBody,
        Map<String, Object> header) {
        String ret = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        RequestConfig config = RequestConfig.custom().setSocketTimeout(300 * 1000).setConnectTimeout(300 * 1000)
            .build();
        post.setConfig(config);
        post.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
        CloseableHttpResponse response = null;
        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            //设置浏览器兼容模式
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //设置请求的编码格式
            entityBuilder.setCharset(StandardCharsets.UTF_8);
            files.forEach((k, v) -> {
                entityBuilder.addBinaryBody("file", v, ContentType.MULTIPART_FORM_DATA, k);
            });
            if (textBody != null) {
                for (Map.Entry<String, Object> entry : textBody.entrySet()) {
                    entityBuilder.addTextBody(entry.getKey(), entry.getValue().toString());
                }
            }
            post.setEntity(entityBuilder.build());
            if (header != null) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                ret = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

}
