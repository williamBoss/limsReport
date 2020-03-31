package com.lims.project.upload.mapper;

import com.lims.project.upload.domain.ExperimentOrderFilePO;
import org.apache.ibatis.annotations.Param;

/**
 * @author KING
 * @version V1.0
 * @Title: TExperimentOrderFileMapper
 * @Package com.lims.project.uploadfile.mapper
 * @Description: ${TODO}(这里用一句话描述这个类的作用)
 * @date 2020/3/18 13:38
 */

public interface ExperimentOrderFileMapper {

    int deleteByPrimaryKey(Integer orderFileId);

    int insert(ExperimentOrderFilePO record);

    int insertSelective(ExperimentOrderFilePO record);

    ExperimentOrderFilePO selectByPrimaryKey(Integer orderFileId);

    int updateByPrimaryKeySelective(ExperimentOrderFilePO record);

    int updateByPrimaryKey(ExperimentOrderFilePO record);

    int updateDeleteFlagByExperimentOrderId(@Param("experimentOrderId") Integer experimentOrderId);


}