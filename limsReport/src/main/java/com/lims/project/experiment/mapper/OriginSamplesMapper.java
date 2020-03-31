package com.lims.project.experiment.mapper;

import com.lims.project.experiment.domain.OriginSamplesDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 推送app数据操作接口
 * 
 * @author yanglin
 * @date 2020-03-23
 */
public interface OriginSamplesMapper
{

    /**
     * 保存
     * @param originSamplesDto
     * @return
     */
   public int inserOrigSamples(OriginSamplesDto originSamplesDto);

    /**
     * 根据服务单号查询推送app的数据
     * @param experimentCode
     * @return
     */
   public List<OriginSamplesDto> selectOriginSamples(@Param("experimentCode") String experimentCode);


}
