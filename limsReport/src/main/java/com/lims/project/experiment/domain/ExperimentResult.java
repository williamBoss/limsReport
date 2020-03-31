package com.lims.project.experiment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 实验单实验结果对象 t_experiment_result
 * 
 * @author yanglin
 * @date 2020-03-18
 */
public class ExperimentResult
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long resultId;

    /** 实验单号 */
    private String experimentCode;

    /** 订单编码(服务单ID) */
    private String orderCode;

    /** 状态 */
    private Integer status;

    /** 删除标志(0代表存在 2代表删除) */
    private String delFlag;

    /** 外键 对应订单主键 */
    private Long experimentOrderId;

    /** 取值字段名字 */
    private String name;

    /** 取值字段key */
    private String key;

    /** 值 */
    private String value;
    /** 创建时间 */
   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public void setResultId(Long resultId) 
    {
        this.resultId = resultId;
    }

    public Long getResultId() 
    {
        return resultId;
    }
    public void setExperimentCode(String experimentCode) 
    {
        this.experimentCode = experimentCode;
    }

    public String getExperimentCode() 
    {
        return experimentCode;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setExperimentOrderId(Long experimentOrderId) 
    {
        this.experimentOrderId = experimentOrderId;
    }

    public Long getExperimentOrderId() 
    {
        return experimentOrderId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setKey(String key) 
    {
        this.key = key;
    }

    public String getKey() 
    {
        return key;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("resultId", getResultId())
            .append("experimentCode", getExperimentCode())
            .append("orderCode", getOrderCode())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("experimentOrderId", getExperimentOrderId())
            .append("name", getName())
            .append("key", getKey())
            .append("value", getValue())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
