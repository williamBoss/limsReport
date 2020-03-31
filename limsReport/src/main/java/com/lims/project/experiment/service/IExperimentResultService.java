package com.lims.project.experiment.service;

import java.util.List;
import com.lims.project.experiment.domain.ExperimentResult;
import com.lims.project.experiment.domain.json.ExperimentResultJson;

/**
 * 实验单实验结果Service接口
 * 
 * @author yanglin
 * @date 2020-03-18
 */
public interface IExperimentResultService 
{
    /**
     * 查询实验单实验结果
     * 
     * @param resultId 实验单实验结果ID
     * @return 实验单实验结果
     */
    public ExperimentResult selectExperimentResultById(Long resultId);

    /**
     * 查询实验单实验结果列表
     * 
     * @param experimentCode 实验单号
     * @return 实验单实验结果集合
     */
    public ExperimentResultJson selectExperimentResultList(String experimentCode);

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
     * 批量删除实验单实验结果
     * 
     * @param resultIds 需要删除的实验单实验结果ID
     * @return 结果
     */
    public int deleteExperimentResultByIds(Long[] resultIds);

    /**
     * 删除实验单实验结果信息
     * 
     * @param resultId 实验单实验结果ID
     * @return 结果
     */
    public int deleteExperimentResultById(Long resultId);
}
