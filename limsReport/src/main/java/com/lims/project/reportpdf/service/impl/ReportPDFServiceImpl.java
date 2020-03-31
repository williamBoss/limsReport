package com.lims.project.reportpdf.service.impl;

import com.lims.common.utils.PDFUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.domain.ExperimentResult;
import com.lims.project.experiment.mapper.ExperimentOrderMapper;
import com.lims.project.experiment.mapper.ExperimentResultMapper;
import com.lims.project.reportpdf.domain.ReportPdfDTO;
import com.lims.project.reportpdf.service.ReportPDFService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KING
 * @version V1.0
 * @Title: PdfService
 * @Package com.lims.project.reportpdf.service
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/17 14:39
 */
@Service
public class ReportPDFServiceImpl implements ReportPDFService {

    @Autowired
    private ExperimentOrderMapper experimentOrderMapper;

    @Autowired
    private ExperimentResultMapper experimentResultMapper;

    @Override
    public byte[] downloadPdfFile(Long experimentOrderId) throws Exception {
        // 读取模板文件
        InputStream is = new BufferedInputStream(new FileInputStream(RuoYiConfig.getPdfTemplatePath()));
        // 查询订单信息
        ExperimentOrderVo orderInfo = experimentOrderMapper.selectExperimentOrderById(experimentOrderId);
        List<ExperimentResult> experimentResultList = experimentResultMapper
            .selectAllByExperimentCodeAndDelFlag(orderInfo.getExperimentCode(), "0");
        // 给模板填充值
        ReportPdfDTO reportPdfDTO = new ReportPdfDTO();
        reportPdfDTO.setProductName(orderInfo.getGoodsName());
        for (ExperimentResult experimentResult : experimentResultList) {
            switch (experimentResult.getKey()) {
                case "tumorArea":
                    reportPdfDTO.setTumorArea(experimentResult.getValue());
                    break;
                case "tumorCellProportion":
                    reportPdfDTO.setTumorCellProportion(experimentResult.getValue());
                    break;
                case "necrosisCellpro":
                    reportPdfDTO.setNecrosisCellProportion("0");
                    break;
                case "PathologicalCancer":
                    reportPdfDTO.setPathologicalAssessment("");
                    break;
                case "image":
                    reportPdfDTO.setImg1(experimentResult.getValue());
                    reportPdfDTO.setFileName1("病理报告图片1");
                    break;
                default:
                    break;
            }
        }
        Map<String, String> keyValues = null;
        keyValues = BeanUtils.describe(reportPdfDTO);
        // 填充pdf模板
        byte[] resultFile = PDFUtils.fillTemplateWithImageAndTxt(is, keyValues);
       /* //result：byte[]
        //要生成文件的路径
        String filePath = RuoYiConfig.getProfile() + File.separator + "pdfFile" + File.separator;
        //要生成文件的名字
        String fileName = "病理评估报告.pdf";
        getFileByBytes(resultFile, filePath, fileName);*/
        return resultFile;
    }

    /**
     * 将Byte数组转换成文件
     *
     * @param bytes
     * @param filePath
     * @param fileName
     */
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
