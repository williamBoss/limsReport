package com.lims.project.upload.mapper;

import com.lims.project.upload.domain.UploadFilePO;

import java.util.List;

/**
 * @author KING
 * @version V1.0
 * @Title: TUploadFileMapper
 * @Package com.lims.project.uploadfile.mapper
 * @Description: 上传文件
 * @date 2020/3/18 13:38
 */
public interface UploadFileMapper {
    /**
     * 删除
     *
     * @param fileId
     * @return
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insert(UploadFilePO record);

    /**
     * 根据字段动态添加
     *
     * @param record
     * @return
     */
    int insertSelective(UploadFilePO record);

    /**
     * 查询单个信息
     *
     * @param fileId
     * @return
     */
    UploadFilePO selectByPrimaryKey(Integer fileId);

    /**
     * 修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UploadFilePO record);

    /**
     * 修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(UploadFilePO record);


    /**
     * 根据订单id查询上传的文件
     * @param fexperimentOrderId
     * @return
     */
    List<UploadFilePO> selectByPrimarOrderId(Long fexperimentOrderId);
}