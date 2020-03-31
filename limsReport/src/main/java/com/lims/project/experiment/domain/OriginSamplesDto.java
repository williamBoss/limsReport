package com.lims.project.experiment.domain;

/**
 * Created by Administrator on 2020/3/24.
 */
public class OriginSamplesDto {

    /**主键*/
    private  Long originId;
    /*原始管编码*/
    private String originTubeCode;
    /*取样容器 */
    private String sampleVessel;
    /*样本分类*/
    private String sampleType;
    /*分类名称*/
    private String typeName;
    /*样本编码 */
    private String sampleCode;
    /*接收备注*/
    private String receiveRemarks;
    /*接收结果: 1：合格，2：风险，9：不合格*/
    private String sampleStatus;
    /*样本性状 */
    private String natureState;

    /** 订单编号 */
    private String  orderCode;
    /** 商品编码 */
    private String  goodsCode;

    /** 检测项目 */
    private String   goodsName;

    /** 实验单号 */
    private String    experimentCode;

    /**检测完成时间*/
    public String updateTime;


    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getOriginTubeCode() {
        return originTubeCode;
    }

    public void setOriginTubeCode(String originTubeCode) {
        this.originTubeCode = originTubeCode;
    }

    public String getSampleVessel() {
        return sampleVessel;
    }

    public void setSampleVessel(String sampleVessel) {
        this.sampleVessel = sampleVessel;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getReceiveRemarks() {
        return receiveRemarks;
    }

    public void setReceiveRemarks(String receiveRemarks) {
        this.receiveRemarks = receiveRemarks;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getNatureState() {
        return natureState;
    }

    public void setNatureState(String natureState) {
        this.natureState = natureState;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OriginSamplesDto{" +
                "originId=" + originId +
                ", originTubeCode='" + originTubeCode + '\'' +
                ", sampleVessel='" + sampleVessel + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", sampleCode='" + sampleCode + '\'' +
                ", receiveRemarks='" + receiveRemarks + '\'' +
                ", sampleStatus='" + sampleStatus + '\'' +
                ", natureState='" + natureState + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", experimentCode='" + experimentCode + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
