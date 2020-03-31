package com.lims.project.reportpdf.service;

import java.io.FileNotFoundException;

/**
 * @author KING
 * @version V1.0
 * @Title: PdfService
 * @Package com.lims.project.reportpdf.service
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/17 14:39
 */
public interface ReportPDFService {

    /**
     * 生成下载报告pdf文件
     *
     * @param experimentOrderId
     * @throws FileNotFoundException
     * @return
     */
    public byte[] downloadPdfFile(Long experimentOrderId) throws Exception;

}
