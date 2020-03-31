package com.lims.project.reportpdf.domain;

/**
 * @author KING
 * @version V1.0
 * @Title: ReportPdfDTO
 * @Package com.lims.project.reportpdf.domain
 * @Description: pdf模板数据对应实体类(这里用一句话描述这个类的作用)
 * @date 2020/3/17 14:58
 */
public class ReportPdfDTO {

    /**
     * 项目名称
     */
    private String productName;

    /**
     * 图片
     */
    private String img1;

    /**
     * 图片名称
     */
    private String fileName1;

    private String img2;

    private String fileName2;

    /**
     * 肿瘤细胞占比
     */
    private String tumorCellProportion;

    /**
     * 肿瘤面积
     */
    private String tumorArea;

    /**
     * 坏死细胞占比
     */
    private String necrosisCellProportion;

    /**
     * 组织病理评估情况
     */
    private String pathologicalAssessment;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getFileName2() {
        return fileName2;
    }

    public void setFileName2(String fileName2) {
        this.fileName2 = fileName2;
    }

    public String getTumorCellProportion() {
        return tumorCellProportion;
    }

    public void setTumorCellProportion(String tumorCellProportion) {
        this.tumorCellProportion = tumorCellProportion;
    }

    public String getTumorArea() {
        return tumorArea;
    }

    public void setTumorArea(String tumorArea) {
        this.tumorArea = tumorArea;
    }

    public String getNecrosisCellProportion() {
        return necrosisCellProportion;
    }

    public void setNecrosisCellProportion(String necrosisCellProportion) {
        this.necrosisCellProportion = necrosisCellProportion;
    }

    public String getPathologicalAssessment() {
        return pathologicalAssessment;
    }

    public void setPathologicalAssessment(String pathologicalAssessment) {
        this.pathologicalAssessment = pathologicalAssessment;
    }
}
