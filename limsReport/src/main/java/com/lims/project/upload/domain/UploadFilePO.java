package com.lims.project.upload.domain;

import java.util.Date;

/**
 * @author KING
 * @version V1.0
 * @Title: TUploadFile
 * @Package com.lims.project.uploadfile.domain
 * @Description: ${TODO}(这里用一句话描述这个类的作用)
 * @date 2020/3/18 13:38
 */

public class UploadFilePO {
    /**
     * 主键id
     */
    private Integer fileId;

    /**
     * 上传文件原名称
     */
    private String originFileName;

    /**
     * 上传文件现在生成的文件名称
     */
    private String newFileName;

    /**
     * 文件保存路径
     */
    private String savePath;

    /**
     * 删除：0.否 1.是
     */
    private Integer deleteFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
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