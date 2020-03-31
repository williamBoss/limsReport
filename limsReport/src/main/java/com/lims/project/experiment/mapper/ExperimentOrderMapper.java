package com.lims.project.experiment.mapper;

import com.lims.project.experiment.domain.ExperimentOrder;
import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.domain.OrderInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 检测订单Mapper接口
 *
 * @author yanglin
 * @date 2020-03-12
 */
public interface ExperimentOrderMapper {

    /**
     * 查询检测订单
     *
     * @param experimentOrderId 检测订单ID
     * @return 检测订单
     */
    public ExperimentOrderVo selectExperimentOrderById(Long experimentOrderId);

    /**
     * 查询检测订单列表
     *
     * @param experimentOrder 检测订单
     * @return 检测订单集合
     */
    public List<ExperimentOrderVo> selectExperimentOrderList(ExperimentOrder experimentOrder);

    /**
     * 新增检测订单
     *
     * @param orderInfoDto 检测订单
     * @return 结果
     */
    public int insertExperimentOrder(OrderInfoDto orderInfoDto);

    /**
     * 检测订单分配
     *
     * @param orderInfoDto 检测订单
     * @return 结果
     */
    public int updateExperimentOrder(OrderInfoDto orderInfoDto);

    /**
     * 删除检测订单
     *
     * @param experimentOrderId 检测订单ID
     * @return 结果
     */
    public int deleteExperimentOrderById(Long experimentOrderId);

    /**
     * 批量删除检测订单
     *
     * @param experimentOrderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExperimentOrderByIds(Long[] experimentOrderIds);

    /**
     * 根据服务单号查询检测订单信息
     *
     * @param experimentCode
     * @return
     */
    public OrderInfoDto selectByExperimentCode(String experimentCode);

    /**
     * 删除数据
     *
     * @param experimentCode
     * @return
     */
    public int updatedelFlag(String experimentCode);

    /**
     * 查询导出列表
     *
     * @param experimentOrderIds
     * @return
     */
    public List<ExperimentOrderVo> selectExportList(@Param("experimentOrderIds") Long[] experimentOrderIds);

    /**
     * 回退
     *
     * @param orderInfoDto
     * @return
     */
    public int updateBack(OrderInfoDto orderInfoDto);

    /**
     * 根据订单号获取信息
     *
     * @param orderCode
     * @return
     */
    public ExperimentOrderVo selectExperimentOrderByOrderCode(@Param("orderCode") String orderCode);
}
