package com.lims.project.experiment.mapper;

import com.lims.project.experiment.domain.ExperimentResult;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 实验单实验结果Mapper接口
 *
 * @author yanglin
 * @date 2020-03-18
 */
public interface ExperimentResultMapper {

    /**
     * 查询实验单实验结果
     *
     * @param resultId 实验单实验结果ID
     * @return 实验单实验结果
     */
    public ExperimentResult selectExperimentResultById(Long resultId);

    /**
     * 获取实验结果
     *
     * @param experimentCode
     * @param delFlag
     * @return
     */
    public List<ExperimentResult> selectAllByExperimentCodeAndDelFlag(@Param("experimentCode") String experimentCode,
        @Param("delFlag") String delFlag);

    /**
     * 查询实验单实验结果列表
     *
     * @param experimentResult 实验单实验结果
     * @return 实验单实验结果集合
     */
    public List<ExperimentResult> selectExperimentResultList(ExperimentResult experimentResult);

    /**
     * 新增实验单实验结果
     *
     * @param experimentResult 实验单实验结果
     * @return 结果
     */
    public int insertExperimentResult(ExperimentResult experimentResult);

    /**
     * 修改实验单实验结果
     *
     * @param experimentResult 实验单实验结果
     * @return 结果
     */
    public int updateExperimentResult(ExperimentResult experimentResult);

    /**
     * 删除实验单实验结果
     *
     * @param resultId 实验单实验结果ID
     * @return 结果
     */
    public int deleteExperimentResultById(Long resultId);

    /**
     * 批量删除实验单实验结果
     *
     * @param resultIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExperimentResultByIds(Long[] resultIds);

    public int updateDelFlag(String experimentCode);
}
