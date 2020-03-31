package com.lims.project.reportpdf.controller;

import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.reportpdf.service.ReportPDFService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KING
 * @version V1.0
 * @Title: ReportPDF
 * @Package com.lims.project.reportpdf.controller
 * @Description: 病理报告pdf(这里用一句话描述这个类的作用)
 * @date 2020/3/17 14:36
 */
@Api("下载报告PDF")
@RestController
@RequestMapping("/downloadReport")
public class DownloadReportController extends BaseController {

    @Autowired
    private ReportPDFService reportPDFService;

    @PostMapping("/downloadReportFiles")
    @ApiOperation(value = "下载报告")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "experimentOrderId", value = "实验订单id", required = true, dataType = "int"),})
    public AjaxResult downloadPdfFile(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "experimentOrderId", required = true) Integer experimentOrderId) {
        AjaxResult ajaxResult = AjaxResult.error("下载报告失败");
        // 下载逻辑：
        ByteArrayInputStream byteArrayInputStream = null;
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        try {
            // 设置文件名，fileName是下载文件的文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("病理报告.pdf", "UTF-8"));
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            //需要下载文件的字节数组
            byte[] buffer = new byte[1024];
            byte[] modelByte = reportPDFService.downloadPdfFile(experimentOrderId.longValue());
            byteArrayInputStream = new ByteArrayInputStream(modelByte);
            OutputStream out = response.getOutputStream();
            int i = byteArrayInputStream.read(buffer);
            while (i != -1) {
                out.write(buffer, 0, i);
                i = byteArrayInputStream.read(buffer);
            }
            out.flush();
            ajaxResult = AjaxResult.success("下载报告成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ajaxResult;
    }

}
