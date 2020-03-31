package com.lims.project.experiment.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lims.common.constant.Constants;
import com.lims.common.constant.ExperimentOrderConstants;
import com.lims.common.exception.CustomException;
import com.lims.common.utils.DateUtils;
import com.lims.common.utils.http.HttpUtils;
import com.lims.common.utils.security.Md5Utils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.experiment.domain.json.ExperimentResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lims.project.experiment.mapper.ExperimentResultMapper;
import com.lims.project.experiment.domain.ExperimentResult;
import com.lims.project.experiment.service.IExperimentResultService;

/**
 * 实验单实验结果Service业务层处理
 * 
 * @author yanglin
 * @date 2020-03-18
 */
@Service
public class ExperimentResultServiceImpl implements IExperimentResultService 
{
    @Autowired
    private ExperimentResultMapper experimentResultMapper;

    /**
     * 查询实验单实验结果
     * 
     * @param resultId 实验单实验结果ID
     * @return 实验单实验结果
     */
    @Override
    public ExperimentResult selectExperimentResultById(Long resultId)
    {
        return experimentResultMapper.selectExperimentResultById(resultId);
    }

    /**
     * 查询实验单实验结果列表
     * 
     * @param experimentCode 实验单号
     * @return 实验单实验结果
     */
    @Override
    public ExperimentResultJson selectExperimentResultList(String experimentCode)
    {
        ExperimentResultJson listResult=null;
        //随机码 code
        String code= Constants.CODE;
        //请求时间 ts
        Integer ts= RuoYiConfig.getTs();
        //请求秘钥
        String  signKey= RuoYiConfig.getSignKey();
       String sign= Md5Utils.hash((signKey + ts + code +experimentCode)).toUpperCase();
       String  param="code="+code+ "&experimentCode=" +experimentCode+ "&ts="+ts;
       String  result= HttpUtils.sendPost(ExperimentOrderConstants.LIMS_ORDER_INFO_URL,param,sign);
        JSONObject resultJson = JSON.parseObject(result);
        if(ExperimentOrderConstants.STATUS.equals(resultJson.get("code"))&&resultJson.get("data")!=null) {
             listResult = JSON.parseObject(resultJson.get("data").toString(), ExperimentResultJson.class);
        }
        if(listResult==null){
            throw new CustomException("拉取数据失败");
        }
      return  listResult;
    }

    /**
     * 新增实验单实验结果
     * 
     * @param experimentResult 实验单实验结果
     * @return 结果
     */
    @Override
    public int insertExperimentResult(ExperimentResult experimentResult)
    {
        experimentResult.setCreateTime(DateUtils.getNowDate());
        return experimentResultMapper.insertExperimentResult(experimentResult);
    }

    /**
     * 修改实验单实验结果
     * 
     * @param experimentResult 实验单实验结果
     * @return 结果
     */
    @Override
    public int updateExperimentResult(ExperimentResult experimentResult)
    {
        experimentResult.setUpdateTime(DateUtils.getNowDate());
        return experimentResultMapper.updateExperimentResult(experimentResult);
    }

    /**
     * 批量删除实验单实验结果
     * 
     * @param resultIds 需要删除的实验单实验结果ID
     * @return 结果
     */
    @Override
    public int deleteExperimentResultByIds(Long[] resultIds)
    {
        return experimentResultMapper.deleteExperimentResultByIds(resultIds);
    }

    /**
     * 删除实验单实验结果信息
     * 
     * @param resultId 实验单实验结果ID
     * @return 结果
     */
    @Override
    public int deleteExperimentResultById(Long resultId)
    {
        return experimentResultMapper.deleteExperimentResultById(resultId);
    }

    public static void main(String[] args)
    {

        ExperimentResultServiceImpl im=new ExperimentResultServiceImpl();
        System.out.println("lims报告外挂管理系统启动成功");
        im.selectExperimentResultList("SY2003110067");
    }

}
