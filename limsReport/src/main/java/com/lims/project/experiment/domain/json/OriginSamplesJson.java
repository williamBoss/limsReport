package com.lims.project.experiment.domain.json;

/**
 * Created by Administrator on 2020/3/20.
 */
public class OriginSamplesJson {
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
}
