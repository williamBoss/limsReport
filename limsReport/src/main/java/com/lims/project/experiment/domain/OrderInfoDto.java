package com.lims.project.experiment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lims.framework.aspectj.lang.annotation.Excel;
import java.util.Date;

/**
 * Created by Administrator on 2020/3/17.
 */
public class OrderInfoDto extends ExperimentOrder {

    /**
     * 临床信息
     */
    private String clinicalInfo;

    /**
     * 医院
     */
    private String department;

    /**
     * 医生邮箱
     */
    private String doctorEmail;

    /**
     * 主治医生
     */
    private String doctorName;

    /**
     * 医生电话
     */
    private String doctorPhone;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 取材来源
     */
    private String histologicOrigin;

    /**
     * 组织取材部位
     */
    private String histologicPosition;

    /**
     * 医院名称
     */
    @Excel(name = "医院名称")
    private String hospitalName;

    /**
     * 销售经理邮箱
     */
    private String managerEmail;

    /**
     * 报告接收人地址
     */
    private String reportreceptionAddress;

    /**
     * 报告接收人邮箱
     */
    private String reportreceptionEmail;

    /**
     * 报告接收人姓名
     */
    private String reportreceptionName;

    /**
     * 报告接收人电话
     */
    private String reportreceptionPhone;

    /**
     * 销售人邮箱
     */
    private String salerEmail;

    /**
     * 销售人姓名
     */
    private String salerName;

    /**
     * 销售电话
     */
    private String salerPhone;

    /**
     * 应出报告时间
     */
    private String timeSampling;
    /**
     * 患者年龄
     */
    private Integer patientAge;

    /**
     * 患者姓名
     */
    @Excel(name = "患者姓名")
    private String patientName;

    /**
     * 联系电话
     */
    private String patientPhone;

    /**
     * 用户性别（1男 2女 3未知）
     */
    private String patientGender;

    /**
     * 操作者id
     */
    @Excel(name = "操作者id")
    private Long userId;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaderCreateTime;

    /**
     * 订单分配时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaderUpdateTime;
    /**
     * 状态
     */
    @Excel(name = "状态")
    private String leaderStatus;

    /**
     * 删除标志(0代表存在 2代表删除)
     */
    private String delFlag;
    /**
     * 排序
     */
    private Integer orderSort;
    @Excel(name = "操作者名字")
    private String userName;
    /**
     * 病理评估报告节点的值
     */
    private String nodeKey;
    /**
     * 前端页面传过来的标示
     */
    private String identification;

    /**
     * 报告下载开始时间
     */
    private String start;

    /**
     * 报告下载结束时间
     */
    private String end;

    public String getClinicalInfo() {
        return clinicalInfo;
    }

    public void setClinicalInfo(String clinicalInfo) {
        this.clinicalInfo = clinicalInfo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
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

    public String getSalerEmail() {
        return salerEmail;
    }

    public void setSalerEmail(String salerEmail) {
        this.salerEmail = salerEmail;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getSalerPhone() {
        return salerPhone;
    }

    public void setSalerPhone(String salerPhone) {
        this.salerPhone = salerPhone;
    }

    public String getTimeSampling() {
        return timeSampling;
    }

    public void setTimeSampling(String timeSampling) {
        this.timeSampling = timeSampling;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public Date getLeaderCreateTime() {
        return leaderCreateTime;
    }

    public void setLeaderCreateTime(Date leaderCreateTime) {
        this.leaderCreateTime = leaderCreateTime;
    }

    public Date getLeaderUpdateTime() {
        return leaderUpdateTime;
    }

    public void setLeaderUpdateTime(Date leaderUpdateTime) {
        this.leaderUpdateTime = leaderUpdateTime;
    }

    public String getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(String leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "OrderInfoDto{" +
                "clinicalInfo='" + clinicalInfo + '\'' +
                ", department='" + department + '\'' +
                ", doctorEmail='" + doctorEmail + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorPhone='" + doctorPhone + '\'' +
                ", expressNumber='" + expressNumber + '\'' +
                ", histologicOrigin='" + histologicOrigin + '\'' +
                ", histologicPosition='" + histologicPosition + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", reportreceptionAddress='" + reportreceptionAddress + '\'' +
                ", reportreceptionEmail='" + reportreceptionEmail + '\'' +
                ", reportreceptionName='" + reportreceptionName + '\'' +
                ", reportreceptionPhone='" + reportreceptionPhone + '\'' +
                ", salerEmail='" + salerEmail + '\'' +
                ", salerName='" + salerName + '\'' +
                ", salerPhone='" + salerPhone + '\'' +
                ", timeSampling='" + timeSampling + '\'' +
                ", patientAge=" + patientAge +
                ", patientName='" + patientName + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientGender='" + patientGender + '\'' +
                ", userId=" + userId +
                ", leaderCreateTime=" + leaderCreateTime +
                ", leaderUpdateTime=" + leaderUpdateTime +
                ", leaderStatus='" + leaderStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", orderSort=" + orderSort +
                ", userName='" + userName + '\'' +
                ", nodeKey='" + nodeKey + '\'' +
                ", identification='" + identification + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
