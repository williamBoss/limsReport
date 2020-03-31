package com.lims.project.sendAPP.controller;

import com.alibaba.fastjson.JSONObject;
import com.lims.common.utils.StringUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.sendAPP.service.SendAppService;
import com.lims.project.sendAPP.util.OpenApiTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KING
 * @version V1.0
 * @Title: SendAppSampleController
 * @Package com.lims.project.sendAPP.controller
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/24 17:48
 */
@Api("推送app")
@RestController
@RequestMapping("/sendAppMessage")
public class SendAppSampleController extends BaseController {

    private Logger log = LoggerFactory.getLogger(SendAppSampleController.class);

    @Autowired
    private SendAppService sendAppService;

    @ApiOperation("发送app推送")
    @ApiImplicitParam(name = "experimentOrderId", value = "实验订单id", required = true, dataType = "int")
    @PostMapping("/sendToApp")
    public AjaxResult sendToApp(@RequestParam("experimentOrderId") Long experimentOrderId) {
        try {
            sendAppService.sendAppInfo(experimentOrderId);
            return AjaxResult.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return AjaxResult.error("发送失败");
    }

    /**
     * 获取订单项检测报告
     */
    @PostMapping(value = "getBioinformaticsReport")
    public ResponseEntity<FileSystemResource> getBioinformaticsReport(@RequestBody(required = false) String json,
        HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(json)) {
            response.sendError(403, "请求参数异常");
        } else {
            boolean bool = OpenApiTool.checkSignedUrl(request, RuoYiConfig.getAppSendReportSignKey(), json);
            if (bool) {
                JSONObject jb = JSONObject.parseObject(json);
                System.out.println(jb);
                String orderCode = jb.getString("orderCode");
                String productCode = jb.getString("productCode");
                if (StringUtils.isEmpty(orderCode) || StringUtils.isEmpty(productCode)) {
                    response.sendError(403, "请求参数异常");
                } else {
                    return sendAppService.getReportFile(response, orderCode);
                }
            } else {
                response.sendError(403, "验证不通过");
            }
        }
        return null;
    }

    @ApiOperation("推送app报告文件")
    @ApiImplicitParam(name = "experimentOrderId", value = "实验订单id", required = true, dataType = "int")
    @PostMapping("/sendToAppReportFiles")
    public AjaxResult sendToAppReportFiles(@RequestParam("experimentOrderId") Long experimentOrderId) {
        try {
            String url = "http://127.0.0.1:8089/receiveFile/receiveReportFile";
            Map<String, File> files = new HashMap<>();
            files.put("病理评估报告.pdf", new File("C:\\Users\\kingz\\Desktop\\病理评估报告模板_v1.pdf"));
            files.put("智能台灯.pdf", new File("C:\\Users\\kingz\\Desktop\\基于STM32的智能台灯.pdf"));
            files.put("Java面试突击.pdf", new File("C:\\Users\\kingz\\Desktop\\Java面试突击-V3.0.pdf"));
            sendAppService.doUpload(url, files, null, null);
            return AjaxResult.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return AjaxResult.error("发送失败");
    }

    public static void main(String[] args) {
        Long ts = System.currentTimeMillis();
        System.out.println(ts);
        String code = "5B5B83D2251211E982EC005056C00008";
        // String sign ="DD160F08921D9378A37B5D67E337B100";
        String key = "b9ca7bc2d6149a27d2a3fc0cc09c0cab";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderCode", "LS2020030601");
        map.put("productCode", "CPX04008");
        String body = JSONObject.toJSONString(map);
        System.out.println(body);
        String msg = key + ts + code + body;
        String sign = DigestUtils.md5Hex(msg).toUpperCase();
        System.out.println(sign);
    }

}
