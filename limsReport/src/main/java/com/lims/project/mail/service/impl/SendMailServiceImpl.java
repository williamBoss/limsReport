package com.lims.project.mail.service.impl;

import com.google.common.collect.Maps;
import com.lims.common.constant.Constants;
import com.lims.common.utils.MailUtil;
import com.lims.common.utils.StringUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.mapper.ExperimentOrderMapper;
import com.lims.project.mail.domain.MailDTO;
import com.lims.project.mail.service.SendMailService;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.mapper.UploadFileMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KING
 * @version V1.0
 * @Title: SendMailServiceImpl
 * @Package com.lims.project.mail.service.impl
 * @Description: 发送邮件(这里用一句话描述这个类的作用)
 * @date 2020/3/16 17:39
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private ExperimentOrderMapper experimentOrderMapper;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    /**
     * 发送邮件
     *
     * @param experimentOrderId
     * @return
     */
    @Override
    public boolean sendMail(Long experimentOrderId) {
        // 查询订单信息
        ExperimentOrderVo orderInfo = experimentOrderMapper.selectExperimentOrderById(experimentOrderId);
        // 拼接邮件内容
        String patientName = orderInfo.getPatientName();
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        String goodsName = orderInfo.getGoodsName();
        Map<String, String> replaceValue = Maps.newHashMap();
        replaceValue.put("patientName", patientName);
        replaceValue.put("date", date);
        replaceValue.put("goodsName", goodsName);
        StrSubstitutor strSubstitutor = new StrSubstitutor(replaceValue);
        String template = "<html><head><style type=\"text/css\"> p { padding-left: 50px; } table { padding-left: 50px;"
            + "border: 0; }</style></head><body> 尊敬的客户您好：<br /><p> "
            + "感谢您选择先声诊断。附件是${patientName}-${date}-${goodsName}的检测报告，敬请查收。</p> " + "祝好！</body></html>";
        String content = strSubstitutor.replace(template);
        // 查询附件列表
        List<UploadFilePO> listFiles = uploadFileMapper.selectByPrimarOrderId(orderInfo.getExperimentOrderId());
        List<String> fileLists = new ArrayList<>(listFiles.size());
        List<String> fileNames = new ArrayList<>(listFiles.size());
        for (UploadFilePO listFile : listFiles) {
            String path = listFile.getSavePath();
            int dirLastIndex = Constants.RESOURCE_PREFIX.length() + 1;
            String currentDir = StringUtils.substring(path, dirLastIndex);
            String filePath = RuoYiConfig.getProfile() + "/" + currentDir;
            fileLists.add(filePath);
            fileNames.add(listFile.getOriginFileName());
        }
        // 附件文件
        String[] fileList = fileLists.toArray(new String[0]);
        // 附件名称
        String[] names = fileNames.toArray(new String[0]);
        // 抄送人
        String[] cc = {};
        // 收件人
        String[] to = {orderInfo.getManagerEmail(), orderInfo.getSalerEmail()};

        // 设置邮件发送信息
        MailDTO mail = new MailDTO();
        mail.setCcArray(cc);
        mail.setToArray(to);
        mail.setSubject(patientName + "-" + date + "-" + goodsName);
        mail.setContent(content);
        mail.setFileNameArray(names);
        mail.setFileSrcArray(fileList);
        mail.setHost(RuoYiConfig.getEmailHost());
        mail.setAccount(RuoYiConfig.getEmailAccount());
        mail.setPassword(RuoYiConfig.getEmailPassword());
        return MailUtil.sendMail(mail);
    }

}
