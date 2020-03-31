package com.lims.project.download.controller;

import com.lims.common.constant.Constants;
import com.lims.common.utils.StringUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.service.UploadFileService;
import io.swagger.annotations.Api;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KING
 * @version V1.0
 * @Title: DownloadFIleController
 * @Package com.lims.project.download.controller
 * @Description: 下载文件(这里用一句话描述这个类的作用)
 * @date 2020/3/19 11:07
 */
@Api
@RestController
@RequestMapping("/download/file")
public class DownloadFileController extends BaseController {

    private Logger log = LoggerFactory.getLogger(DownloadFileController.class);
    private UploadFileService uploadFileService;

    @GetMapping("/fileDownLoad")
    public AjaxResult fileDownLoad(HttpServletResponse response,
        @RequestParam("experimentOrderId") Long experimentOrderId) {
        List<UploadFilePO> list = uploadFileService.getFileList(experimentOrderId);
        if (list.size() == 0) {
            return AjaxResult.error("下载文件不存在");
        }
        UploadFilePO fileInfo = list.get(0);
        String path = fileInfo.getSavePath();
        int dirLastIndex = Constants.RESOURCE_PREFIX.length() + 1;
        String currentDir = StringUtils.substring(path, dirLastIndex);
        String filePath = RuoYiConfig.getProfile() + "/" + currentDir;
        File file = new File(filePath);
        String downloadName = fileInfo.getOriginFileName();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error("下载失败");
        }
        return AjaxResult.success("下载成功");
    }

}
