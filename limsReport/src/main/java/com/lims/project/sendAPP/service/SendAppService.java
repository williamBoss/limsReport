package com.lims.project.sendAPP.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

/**
 * @author KING
 * @version V1.0
 * @Title: SendAppService
 * @Package com.lims.project.sendAPP
 * @Description: 推送App(这里用一句话描述这个类的作用)
 * @date 2020/3/23 20:12
 */
public interface SendAppService {

    public void sendAppInfo(Long experimentOrderId) throws Exception;

    public String doUpload(String url, Map<String, File> files, Map<String, Object> textBody,
        Map<String, Object> header);

    public ResponseEntity<FileSystemResource> getReportFile(HttpServletResponse response, String orderCode) throws IOException;

}
