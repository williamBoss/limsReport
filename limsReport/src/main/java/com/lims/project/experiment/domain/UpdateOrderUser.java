package com.lims.project.experiment.domain;

import com.lims.framework.aspectj.lang.annotation.Excel;
import com.lims.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 操作者记录对象 t_update_order_user
 * 
 * @author yanglin
 * @date 2020-03-23
 */
public class UpdateOrderUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单表主键 */
    @Excel(name = "订单表主键")
    private Long experimentOrderId;

    /** 操作状态 */
    @Excel(name = "操作状态")
    private String leaderStatus;

    /** 操作者id */
    @Excel(name = "操作者id")
    private Long userId;

    /** 操作者姓名 */
    @Excel(name = "操作者姓名")
    private String userName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setExperimentOrderId(Long experimentOrderId) 
    {
        this.experimentOrderId = experimentOrderId;
    }

    public Long getExperimentOrderId() 
    {
        return experimentOrderId;
    }
    public void setLeaderStatus(String leaderStatus) 
    {
        this.leaderStatus = leaderStatus;
    }

    public String getLeaderStatus() 
    {
        return leaderStatus;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("experimentOrderId", getExperimentOrderId())
            .append("leaderStatus", getLeaderStatus())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .toString();
    }
}
