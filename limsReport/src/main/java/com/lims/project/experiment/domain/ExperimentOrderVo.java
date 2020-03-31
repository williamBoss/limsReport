package com.lims.project.experiment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lims.framework.aspectj.lang.annotation.Excel;
import com.lims.project.upload.domain.UploadFilePO;

import java.util.Date;
import java.util.List;

/**
 * 订单列表
 * 返回给前端的数据对象
 */
public class ExperimentOrderVo {
    private static final long serialVersionUID = 1L;
    /** 订单主键 */
    private Long experimentOrderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderCode;
    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;
    /** 检测项目 */
    @Excel(name = "检测项目")
    private String goodsName;
    @Excel(name = "检测报告名称")
    private  String  originFileName;
    /** 应出报告日期 */
    @Excel(name = "应出报告日期")
    public String updateTime;
    /** 订单分配时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "终审完成时间")
    private Date leaderUpdateTime;
    @Excel(name = "备注")
    private  String  rmark;
    /** 销售人姓名 */
    @Excel(name = "销售")
    private String salerName;
    /** 医院名称 */
    @Excel(name = "医院")
    private String hospitalName;
    /** 科室 */
    @Excel(name = "科室")
    private String department;
    /** 打印次数 */
    @Excel(name = "打印次数")
    private int frequency;
    /** 报告接收人姓名 */
    @Excel(name = "报告接收人姓名")
    private String reportreceptionName;
    /** 报告接收人电话 */
    @Excel(name = "收件人联系电话")
    private String reportreceptionPhone;
    /** 报告接收人地址 */
    @Excel(name = "邮寄地址")
    private String reportreceptionAddress;
    /** 报告接收人邮箱 */
    @Excel(name = "电子报告接收邮箱")
    private String reportreceptionEmail;
    /** 销售人邮箱 */
    @Excel(name = "销售邮箱")
    private String salerEmail;
    /** 销售经理邮箱 */
    @Excel(name = "营销经理邮箱")
    private String managerEmail;
    /** 实验单号 */
    private String experimentCode;
    /** 应出报告时间 */
    private String timeSampling;
    /** 状态 */
    private String leaderStatus;

    private String userName;
    /** 病理评估报告节点的值 */
    private String nodeKey;
    /** 销售电话 */
    public String salerPhone;
    /** 联系电话 */
    private String patientPhone;
    /** 用户性别（1男 2女 3未知） */
    private String patientGender;
    /** 操作者id */
    private Long userId;
    /** 医生邮箱 */
    private String doctorEmail;
    /** 主治医生 */
    private String doctorName;
    /** 医生电话 */
    private String doctorPhone;
    /** 快递单号 */
    private String expressNumber;
    /** 取材来源 */
    private String histologicOrigin;
    /** 组织取材部位 */
    private String histologicPosition;
    /** 患者年龄 */
    private Integer patientAge;
    /** 病理信息 */
    private String clinicalInfo;


    /**文件对象*/
    List<UploadFilePO> uploadFilePOList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getExperimentOrderId() {
        return experimentOrderId;
    }

    public void setExperimentOrderId(Long experimentOrderId) {
        this.experimentOrderId = experimentOrderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLeaderUpdateTime() {
        return leaderUpdateTime;
    }

    public void setLeaderUpdateTime(Date leaderUpdateTime) {
        this.leaderUpdateTime = leaderUpdateTime;
    }

    public String getRmark() {
        return rmark;
    }

    public void setRmark(String rmark) {
        this.rmark = rmark;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getReportreceptionName() {
        return reportreceptionName;
    }

    public void setReportreceptionName(String reportreceptionName) {
        this.reportreceptionName = reportreceptionName;
    }

    public String getReportreceptionPhone() {
        return reportreceptionPhone;
    }

    public void setReportreceptionPhone(String reportreceptionPhone) {
        this.reportreceptionPhone = reportreceptionPhone;
    }

    public String getReportreceptionAddress() {
        return reportreceptionAddress;
    }

    public void setReportreceptionAddress(String reportreceptionAddress) {
        this.reportreceptionAddress = reportreceptionAddress;
    }

    public String getReportreceptionEmail() {
        return reportreceptionEmail;
    }

    public void setReportreceptionEmail(String reportreceptionEmail) {
        this.reportreceptionEmail = reportreceptionEmail;
    }

    public String getSalerEmail() {
        return salerEmail;
    }

    public void setSalerEmail(String salerEmail) {
        this.salerEmail = salerEmail;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public String getTimeSampling() {
        return timeSampling;
    }

    public void setTimeSampling(String timeSampling) {
        this.timeSampling = timeSampling;
    }

    public String getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(String leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getSalerPhone() {
        return salerPhone;
    }

    public void setSalerPhone(String salerPhone) {
        this.salerPhone = salerPhone;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getHistologicOrigin() {
        return histologicOrigin;
    }

    public void setHistologicOrigin(String histologicOrigin) {
        this.histologicOrigin = histologicOrigin;
    }

    public String getHistologicPosition() {
        return histologicPosition;
    }

    public void setHistologicPosition(String histologicPosition) {
        this.histologicPosition = histologicPosition;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getClinicalInfo() {
        return clinicalInfo;
    }

    public void setClinicalInfo(String clinicalInfo) {
        this.clinicalInfo = clinicalInfo;
    }

    public List<UploadFilePO> getUploadFilePOList() {
        return uploadFilePOList;
    }

    public void setUploadFilePOList(List<UploadFilePO> uploadFilePOList) {
        this.uploadFilePOList = uploadFilePOList;
    }

    @Override
    public String toString() {
        return "ExperimentOrderVo{" +
                "experimentOrderId=" + experimentOrderId +
                ", orderCode='" + orderCode + '\'' +
                ", patientName='" + patientName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", newFileName='" + originFileName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", leaderUpdateTime=" + leaderUpdateTime +
                ", rmark='" + rmark + '\'' +
                ", salerName='" + salerName + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", department='" + department + '\'' +
                ", frequency=" + frequency +
                ", reportreceptionName='" + reportreceptionName + '\'' +
                ", reportreceptionPhone='" + reportreceptionPhone + '\'' +
                ", reportreceptionAddress='" + reportreceptionAddress + '\'' +
                ", reportreceptionEmail='" + reportreceptionEmail + '\'' +
                ", salerEmail='" + salerEmail + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", experimentCode='" + experimentCode + '\'' +
                ", timeSampling='" + timeSampling + '\'' +
                ", leaderStatus='" + leaderStatus + '\'' +
                ", userName='" + userName + '\'' +
                ", nodeKey='" + nodeKey + '\'' +
                ", salerPhone='" + salerPhone + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientGender='" + patientGender + '\'' +
                ", userId=" + userId +
                ", doctorEmail='" + doctorEmail + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorPhone='" + doctorPhone + '\'' +
                ", expressNumber='" + expressNumber + '\'' +
                ", histologicOrigin='" + histologicOrigin + '\'' +
                ", histologicPosition='" + histologicPosition + '\'' +
                ", patientAge=" + patientAge +
                ", clinicalInfo='" + clinicalInfo + '\'' +
                ", uploadFilePOList=" + uploadFilePOList +
                '}';
    }
}
