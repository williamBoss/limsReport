package com.lims.project.upload.domain;

import java.util.Date;

/**
 * @author KING
 * @version V1.0
 * @Title: TExperimentOrderFile
 * @Package com.lims.project.uploadfile.domain
 * @Description: ${TODO}(这里用一句话描述这个类的作用)
 * @date 2020/3/18 13:38
 */

public class ExperimentOrderFilePO {

    /**
     * 主键id
     */
    private Integer orderFileId;

    /**
     * 订单id
     */
    private Integer experimentOrderId;

    /**
     * 上传文件id
     */
    private Integer fileId;

    /**
     * 删除：0.否 1.是
     */
    private Integer deleteFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    public Integer getOrderFileId() {
        return orderFileId;
    }

    public void setOrderFileId(Integer orderFileId) {
        this.orderFileId = orderFileId;
    }

    public Integer getExperimentOrderId() {
        return experimentOrderId;
    }

    public void setExperimentOrderId(Integer experimentOrderId) {
        this.experimentOrderId = experimentOrderId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
}