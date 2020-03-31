package com.lims.project.sendAPP.domain;

/**
 * @author KING
 * @version V1.0
 * @Title: SendAppDTO
 * @Package com.lims.project.sendAPP.domain
 * @Description: app推送发送数据(这里用一句话描述这个类的作用)
 * @date 2020/3/24 17:41
 */
public class SendAppSampleDTO {

    private String sampleCode;
    private Long timeReceive;
    private Boolean isQualified;

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public Long getTimeReceive() {
        return timeReceive;
    }

    public void setTimeReceive(Long timeReceive) {
        this.timeReceive = timeReceive;
    }

    public Boolean getQualified() {
        return isQualified;
    }

    public void setQualified(Boolean qualified) {
        isQualified = qualified;
    }
}
