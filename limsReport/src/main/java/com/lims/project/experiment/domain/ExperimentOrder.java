package com.lims.project.experiment.domain;
/**
 * 检测订单对象 t_experiment_order
 * 
 * @author yanglin
 * @date 2020-03-12
 */
public class ExperimentOrder
{
    private static final long serialVersionUID = 1L;
    /** 订单主键 */
    private Long experimentOrderId;
    /** 订单编号 */
    private String  orderCode;
    /** 商品编码 */
    private String  goodsCode;
    /** 检测项目 */
    private String   goodsName;
    /** LISM2.0检测项目编码 */
    private String   oldtestCode;
    /** 实验单号 */
    private String    experimentCode;
    /** 模板编码 */
    private String    templateCode;
    /** 模板版本号 */
    private String    templateVersion;
    /** 模板名称 */
    private String      templateName;
    /** 状态 */
    private Integer     status;
    /**检测完成时间*/
    public String updateTime;

    public OrderInfoDto orderInfoVo;

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

    public String getOldtestCode() {
        return oldtestCode;
    }

    public void setOldtestCode(String oldtestCode) {
        this.oldtestCode = oldtestCode;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OrderInfoDto getOrderInfoVo() {
        return orderInfoVo;
    }

    public void setOrderInfoVo(OrderInfoDto orderInfoVo) {
        this.orderInfoVo = orderInfoVo;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ExperimentOrder{" +
                "experimentOrderId=" + experimentOrderId +
                ", orderCode='" + orderCode + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", oldtestCode='" + oldtestCode + '\'' +
                ", experimentCode='" + experimentCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", templateVersion='" + templateVersion + '\'' +
                ", templateName='" + templateName + '\'' +
                ", status=" + status +
                ", updateTime='" + updateTime + '\'' +
                ", orderInfoVo=" + orderInfoVo +
                '}';
    }
}
