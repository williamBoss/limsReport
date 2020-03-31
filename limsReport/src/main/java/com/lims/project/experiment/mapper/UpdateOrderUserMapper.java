package com.lims.project.experiment.mapper;

import com.lims.project.experiment.domain.UpdateOrderUser;

import java.util.List;


/**
 * 操作者记录Mapper接口
 * 
 * @author yanglin
 * @date 2020-03-23
 */
public interface UpdateOrderUserMapper 
{
    /**
     * 查询操作者记录
     * 
     * @param id 操作者记录ID
     * @return 操作者记录
     */
    public UpdateOrderUser selectUpdateOrderUserById(Long id);

    /**
     * 查询操作者记录列表
     * 
     * @param updateOrderUser 操作者记录
     * @return 操作者记录集合
     */
    public UpdateOrderUser selectUpdateOrderUser(UpdateOrderUser updateOrderUser);

    /**
     * 新增操作者记录
     * 
     * @param updateOrderUser 操作者记录
     * @return 结果
     */
    public int insertUpdateOrderUser(UpdateOrderUser updateOrderUser);

    /**
     * 修改操作者记录
     * 
     * @param updateOrderUser 操作者记录
     * @return 结果
     */
    public int updateUpdateOrderUser(UpdateOrderUser updateOrderUser);

    /**
     * 删除操作者记录
     * 
     * @param id 操作者记录ID
     * @return 结果
     */
    public int deleteUpdateOrderUserById(Long id);

    /**
     * 批量删除操作者记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUpdateOrderUserByIds(Long[] ids);
}
