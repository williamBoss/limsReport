package com.lims.project.experiment.service;

import java.util.List;

import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.domain.OrderInfoDto;

/**
 * 检测订单Service接口
 * 
 * @author yanglin
 * @date 2020-03-12
 */
public interface IExperimentOrderService 
{


    /**
     * 查询检测订单
     * 
     * @param experimentOrderId 检测订单ID
     * @return 检测订单
     */
    public ExperimentOrderVo selectExperimentOrderById(Long experimentOrderId);

    /**
     * 查询检测订单列表
     * @param orderInfoDto
     * @return
     */

    public List<ExperimentOrderVo> selectExperimentOrderList(OrderInfoDto orderInfoDto);

    /**
     * 新增检测订单
     * @return 结果
     */
    public int  insertExperimentOrder(String fromTime,String toTime);

    public  void  addOriginSamples(OrderInfoDto orderInfoDto);

    /**
     * 修改检测订单
     * @param experimentOrderIds
     * @param userId
     * @param userName
     * @param leaderStatus
     * @param operationType 0 分配 1 退回
     * @return
     */
    public int updateExperimentOrder(String experimentOrderIds, Long userId, String userName, String leaderStatus,String operationType);

    /**
     * 批量删除检测订单
     * 
     * @param experimentOrderIds 需要删除的检测订单ID
     * @return 结果
     */
    public int deleteExperimentOrderByIds(Long[] experimentOrderIds);

    /**
     * 删除检测订单信息
     * 
     * @param experimentOrderId 检测订单ID
     * @return 结果
     */
    public int deleteExperimentOrderById(Long experimentOrderId);

    /**
     * 查询导出列表
     * @param experimentOrderIds
     * @return
     */
    public  List<ExperimentOrderVo> selectExportList(String experimentOrderIds);

    /**
     * 回退
     * @param experimentOrderId
     * @param userId
     * @param userName
     * @param identification
     * @return
     */
    public   int  updateBack(Long experimentOrderId, Long userId,String userName,
                         String identification);
}
