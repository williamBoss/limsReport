package com.lims.project.experiment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lims.LimsReportApplication;
import com.lims.common.constant.Constants;
import com.lims.common.constant.ExperimentOrderConstants;
import com.lims.common.exception.CustomException;
import com.lims.common.utils.DateUtils;
import com.lims.common.utils.http.HttpUtils;
import com.lims.common.utils.security.Md5Utils;
import com.lims.common.utils.text.LoginUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.experiment.domain.*;
import com.lims.project.experiment.domain.json.ExperimentResultJson;
import com.lims.project.experiment.domain.json.NodeResultListJson;
import com.lims.project.experiment.domain.json.OrderGoodJson;
import com.lims.project.experiment.domain.json.OriginSamplesJson;
import com.lims.project.experiment.domain.json.sampleResultsJson;
import com.lims.project.experiment.mapper.ExperimentOrderMapper;
import com.lims.project.experiment.mapper.ExperimentResultMapper;
import com.lims.project.experiment.mapper.OriginSamplesMapper;
import com.lims.project.experiment.mapper.UpdateOrderUserMapper;
import com.lims.project.experiment.service.IExperimentOrderService;
import com.lims.project.mail.service.SendMailService;
import com.lims.project.system.domain.SysUser;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.mapper.ExperimentOrderFileMapper;
import com.lims.project.upload.mapper.UploadFileMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 检测订单Service业务层处理
 *
 * @author yanglin
 * @date 2020-03-12
 */
@Service
public class ExperimentOrderServiceImpl implements IExperimentOrderService {

    private Logger log = LoggerFactory.getLogger(ExperimentOrderServiceImpl.class);
    @Autowired
    private ExperimentOrderMapper experimentOrderMapper;
    @Autowired
    private ExperimentResultMapper experimentResultMapper;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private ExperimentOrderFileMapper experimentOrderFileMapper;
    @Autowired
    private UpdateOrderUserMapper updateOrderUserMapper;
    @Autowired
    private OriginSamplesMapper originSamplesMapper;

    /**
     * 查询检测订单
     *
     * @param experimentOrderId 检测订单ID
     * @return 检测订单
     */
    @Override
    public ExperimentOrderVo selectExperimentOrderById(Long experimentOrderId) {
        return experimentOrderMapper.selectExperimentOrderById(experimentOrderId);
    }

    /**
     * 查询检测订单列表
     *
     * @return 检测订单
     */
    @Override
    /**
     * 查询检测订单列表
     *
     * @param experimentOrder 检测订单
     * @return 检测订单集合*/
    public List<ExperimentOrderVo> selectExperimentOrderList(OrderInfoDto orderInfoDto) {
        //根据前端传过来的标示
        if (!ExperimentOrderConstants.DFP.equals(orderInfoDto.getIdentification())) {
             SysUser user = LoginUtils.getSessionUserInfo();
            orderInfoDto.setUserId(user.getUserId());
        }
        List<ExperimentOrderVo> list = experimentOrderMapper.selectExperimentOrderList(orderInfoDto);
        List<ExperimentOrderVo> listVo = new ArrayList<>();
        for (ExperimentOrderVo experimentOrderVo : list) {
            List<UploadFilePO> listFiles = uploadFileMapper
                .selectByPrimarOrderId(experimentOrderVo.getExperimentOrderId());
            experimentOrderVo.setUploadFilePOList(listFiles);
            listVo.add(experimentOrderVo);
        }
        return listVo;
    }

    /**
     * 新增检测订单
     *
     * @param fromTime
     * @param toTime
     * @return
     */
    @Override
    @Transactional
    public int insertExperimentOrder(String fromTime, String toTime) {
        int rows = 0;
        //先从lims端拉取数据
        //随机码 code
        String code = Constants.CODE;
        //请求时间 ts
        Integer ts = RuoYiConfig.getTs();
        //秘钥
        String signKey = RuoYiConfig.getSignKey();
        //签名 sign
        String sign = Md5Utils.hash((signKey + ts + code + fromTime + toTime)).toUpperCase();
        String param = "code=" + code + "" + "&fromTime=" + fromTime +
            // "&sign=" + sign +
            "&toTime=" + toTime + "&ts=" + ts;
        //2020-03-17 09:19
        //实验完成订单列表
        String resultOrderList = null;
        //获取实验单实验结果详情信息
        String resultOrderInfot = null;
        try {
            resultOrderList = HttpUtils.sendPost(RuoYiConfig.getLimsOrderListUrl(), param, sign);
            if (resultOrderList != null) {
                JSONObject jsons = JSON.parseObject(resultOrderList);
                if (ExperimentOrderConstants.STATUS.equals(jsons.get("code")) && jsons.get("data") != null) {
                    List<OrderInfoDto> list = JSON.parseArray(jsons.get("data").toString(), OrderInfoDto.class);
                    for (int i = 0; i < list.size(); i++) {
                        OrderInfoDto orderInfoDto = list.get(i);
                        //签名
                        sign = Md5Utils.hash((signKey + ts + code + orderInfoDto.getExperimentCode())).toUpperCase();
                        param = "code=" + code + "&experimentCode=" + orderInfoDto.getExperimentCode() + "&ts=" + ts;
                        //根据试验单拉取 获取实验单实验结果详情信息
                        resultOrderInfot = HttpUtils.sendPost(RuoYiConfig.getLimsOrderInfoUrl(), param, sign);
                        JSONObject resultJson = JSON.parseObject(resultOrderInfot);
                        if (ExperimentOrderConstants.STATUS.equals(resultJson.get("code"))
                            && resultJson.get("data") != null) {
                            ExperimentResultJson listResult = JSON
                                .parseObject(resultJson.get("data").toString(), ExperimentResultJson.class);
                            List<NodeResultListJson> nodeResultListJsons = listResult.getNodeResultList();
                            for (NodeResultListJson nodeResultListJson : nodeResultListJsons) {
                                if ("BL".equals(nodeResultListJson.getNodeKey())) {
                                    orderInfoDto.setNodeKey("BL");
                                    List<sampleResultsJson> sampleResultsJsons = nodeResultListJson.getSampleResults();
                                    for (sampleResultsJson sample : sampleResultsJsons) {
                                        List<ExperimentResult> experimentResults = JSON
                                            .parseArray(sample.getResultVal(), ExperimentResult.class);
                                        //保存检测节点为BL的时候的检测数据
                                        for (ExperimentResult experimentResult : experimentResults) {
                                            experimentResult.setExperimentCode(orderInfoDto.getExperimentCode());
                                            experimentResult.setOrderCode(orderInfoDto.getOrderCode());
                                            //如果有数据删除该服务单号下的检测数据;
                                            experimentResultMapper.updateDelFlag(orderInfoDto.getExperimentCode());
                                            //添加检测信息
                                            experimentResultMapper.insertExperimentResult(experimentResult);
                                        }
                                    }
                                }
                            }
                        }
                        //添加之前查询本地数据库该检测的服务单号是否存在 存在删除在添加
                        OrderInfoDto listOrder = experimentOrderMapper
                            .selectByExperimentCode(orderInfoDto.getExperimentCode());
                        if (listOrder != null) {
                            experimentOrderMapper.updatedelFlag(listOrder.getExperimentCode());

                            experimentOrderMapper.insertExperimentOrder(orderInfoDto);
                            //删除上传的文件
                            experimentOrderFileMapper.updateDeleteFlagByExperimentOrderId(listOrder.getExperimentOrderId().intValue());
                        } else {
                            experimentOrderMapper.insertExperimentOrder(orderInfoDto);
                        }
                        //保存订单对应的实验列表数据
                        addOriginSamples(orderInfoDto);
                    }
                }
            }
            rows = 1;
        } catch (Exception e) {
            rows = 0;
            log.error(e.getMessage());
            throw new CustomException("拉取添加检验订单数据异常");
        }
        return rows;
    }
     //这个方法用来保存推送app的数据
    public void addOriginSamples(OrderInfoDto orderInfoDto) {
        //随机码 code
        String code = Constants.CODE;
        //请求时间 ts
        Integer ts = RuoYiConfig.getTs();
        //秘钥
        String signKey = RuoYiConfig.getSignKey();
        //订单编码
        String orderCode = orderInfoDto.getOrderCode();
        //签名 sign
        String sign = Md5Utils.hash(signKey + ts + code + orderCode).toUpperCase();
        String param = "code=" + code + "&ts=" + ts + "&sign=" + sign + "&orderCode=" + orderCode;
        String originSamples = HttpUtils.sendPost(RuoYiConfig.getLimsOrderDetailsUrl(), param, sign);
        JSONObject jsons = JSON.parseObject(originSamples);
        if (ExperimentOrderConstants.STATUS.equals(jsons.get("code")) && jsons.get("data") != null) {
            JSONObject orderJson = JSON.parseObject(jsons.get("data").toString());
            if(orderJson.get("orderGoodsList")!=null){
                List<OrderGoodJson> list = JSON.parseArray(orderJson.get("orderGoodsList").toString(), OrderGoodJson.class);
                if(list!=null){
                    for (OrderGoodJson orderGoodJson:list) {
                        if(orderGoodJson.getOriginSamples()!=null){
                            for (OriginSamplesJson originSamplesJson:orderGoodJson.getOriginSamples()) {
                                OriginSamplesDto originSamplesDto=new OriginSamplesDto();
                                originSamplesDto.setOrderCode(orderInfoDto.getOrderCode());
                                originSamplesDto.setGoodsCode(orderInfoDto.getGoodsCode());
                                originSamplesDto.setGoodsName(orderInfoDto.getGoodsName());
                                originSamplesDto.setExperimentCode(orderInfoDto.getExperimentCode());
                                originSamplesDto.setUpdateTime(orderInfoDto.getUpdateTime());
                                originSamplesDto.setExperimentCode(orderInfoDto.getExperimentCode());
                                originSamplesDto.setOriginTubeCode(originSamplesJson.getOriginTubeCode());
                                originSamplesDto.setSampleVessel(originSamplesJson.getSampleVessel());
                                originSamplesDto.setSampleType(originSamplesJson.getSampleType());
                                originSamplesDto.setTypeName(originSamplesJson.getTypeName());
                                originSamplesDto.setSampleCode(originSamplesJson.getSampleCode());
                                originSamplesDto.setReceiveRemarks(originSamplesJson.getReceiveRemarks());
                                originSamplesDto.setSampleStatus(originSamplesJson.getSampleStatus());
                                originSamplesDto.setNatureState(originSamplesJson.getNatureState());
                                originSamplesMapper.inserOrigSamples(originSamplesDto);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 检测订单分配
     *
     * @param experimentOrderIds
     * @param userId
     * @param userName
     * @param leaderStatus
     * @return
     */
    @Transactional
    @Override
    public int updateExperimentOrder(String experimentOrderIds, Long userId, String userName, String leaderStatus,
        String operationType) {

        UpdateOrderUser updateOrderUser = null;
        if (ExperimentOrderConstants.OPERATIONTYPE.equals(operationType)) {
            return updateBack(new Long(experimentOrderIds), userId, userName, leaderStatus);
        } else {
            int rows = 0;
            Boolean resoult = false;
            OrderInfoDto orderInfoDto = new OrderInfoDto();
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            if (experimentOrderIds.contains(",")) {
                String[] strs = experimentOrderIds.split(",");
                for (String experimentOrderId : strs) {
                    rows = editOrderStatus(userId, userName, leaderStatus, orderInfoDto, experimentOrderId);
                }
            } else {
                rows = editOrderStatus(userId, userName, leaderStatus, orderInfoDto, experimentOrderIds);
            }
            return rows;
        }
    }

    public int editOrderStatus(Long userId, String userName, String leaderStatus, OrderInfoDto orderInfoDto,
        String experimentOrderId) {
        int rows;
        UpdateOrderUser updateOrderUser;
        orderInfoDto.setUserId(userId);
        orderInfoDto.setExperimentOrderId(Long.parseLong(experimentOrderId));
        orderInfoDto.setUserName(userName);
        orderInfoDto.setLeaderStatus(leaderStatus);
        orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
        rows = experimentOrderMapper.updateExperimentOrder(orderInfoDto);
        //记录每一步的操作者
        if (userId != null) {
            updateOrderUser = new UpdateOrderUser();
            updateOrderUser.setLeaderStatus(leaderStatus);
            updateOrderUser.setExperimentOrderId(Long.parseLong(experimentOrderId));
            updateOrderUser.setUserId(userId);
            updateOrderUser.setUserName(userName);
            updateOrderUserMapper.insertUpdateOrderUser(updateOrderUser);
        }
        if (ExperimentOrderConstants.STATUSYSH.equals(leaderStatus)) {
            boolean resoult = sendMailService.sendMail(Long.parseLong(experimentOrderId));
            if (!resoult) {
                rows = 3;
            }
        }
        return rows;
    }

    /**
     * 批量删除检测订单
     *
     * @param experimentOrderIds 需要删除的检测订单ID
     * @return 结果
     */
    @Override
    public int deleteExperimentOrderByIds(Long[] experimentOrderIds) {
        return experimentOrderMapper.deleteExperimentOrderByIds(experimentOrderIds);
    }

    /**
     * 删除检测订单信息
     *
     * @param experimentOrderId 检测订单ID
     * @return 结果
     */
    @Override
    public int deleteExperimentOrderById(Long experimentOrderId) {
        return experimentOrderMapper.deleteExperimentOrderById(experimentOrderId);
    }
    /**
     * 查询导出列表
     *
     * @param experimentOrderIds
     * @return
     */
    public List<ExperimentOrderVo> selectExportList(String experimentOrderIds) {
        Long[] list = null;
        if (experimentOrderIds.contains(",")) {
            String[] strs = experimentOrderIds.split(",");
            list = new Long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                list[i] = Long.parseLong(strs[i]);
            }
        } else {
            list = new Long[1];
            list[0] = Long.parseLong(experimentOrderIds);
        }
        List<ExperimentOrderVo> orderVolist=experimentOrderMapper.selectExportList(list);
        List<ExperimentOrderVo> resultRist=new ArrayList<>();
        for (ExperimentOrderVo vo : orderVolist ) {
            String originFileName="";
            if(vo.getUploadFilePOList()!=null){
                for (UploadFilePO po:vo.getUploadFilePOList()) {
                    originFileName+=  po.getOriginFileName()+";";
                }
            }
            vo.setOriginFileName(originFileName);
            resultRist.add(vo);
        }
        return resultRist;
    }

    /**
     * 回退
     *
     * @param experimentOrderId
     * @param userId
     * @param userName
     * @param leaderStatus
     * @return
     */
    @Transactional
    public int updateBack(Long experimentOrderId, Long userId, String userName, String leaderStatus) {
        int rows = 0;
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        String status = "";
        UpdateOrderUser updateOrderUser = null;
        if (leaderStatus.equals("1")) {
            status = "0";
            orderInfoDto.setExperimentOrderId(experimentOrderId);
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            orderInfoDto.setExperimentOrderId(experimentOrderId);
            orderInfoDto.setLeaderStatus(status);
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            orderInfoDto.setUserName(null);
            orderInfoDto.setUserId(null);
            rows = experimentOrderMapper.updateBack(orderInfoDto);
            //删除上一个操作者
            updateOrderUser = new UpdateOrderUser();
            updateOrderUser.setLeaderStatus(leaderStatus);
            updateOrderUser.setExperimentOrderId(experimentOrderId);
            updateOrderUserMapper.updateUpdateOrderUser(updateOrderUser);
        } else if (leaderStatus.equals("2")) {
            status = "0";
            orderInfoDto.setExperimentOrderId(experimentOrderId);
            orderInfoDto.setLeaderStatus(status);
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            orderInfoDto.setExperimentOrderId(experimentOrderId);
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            orderInfoDto.setUserName(null);
            orderInfoDto.setUserId(null);
            rows = experimentOrderMapper.updateBack(orderInfoDto);
            //删除上传的文件
            experimentOrderFileMapper.updateDeleteFlagByExperimentOrderId(experimentOrderId.intValue());
            //删除上一个操作者
            updateOrderUser = new UpdateOrderUser();
            updateOrderUser.setLeaderStatus(leaderStatus);
            updateOrderUser.setExperimentOrderId(experimentOrderId);
            updateOrderUserMapper.updateUpdateOrderUser(updateOrderUser);
        } else if (leaderStatus.equals("3")) {
            status = "2";
            updateOrderUser = new UpdateOrderUser();
            updateOrderUser.setLeaderStatus(leaderStatus);
            updateOrderUser.setExperimentOrderId(experimentOrderId);
            //查询上传文件的操作者
            UpdateOrderUser resOrderUser = updateOrderUserMapper.selectUpdateOrderUser(updateOrderUser);
            orderInfoDto.setExperimentOrderId(experimentOrderId);
            orderInfoDto.setLeaderStatus(status);
            orderInfoDto.setLeaderUpdateTime(DateUtils.getNowDate());
            orderInfoDto.setUserName(resOrderUser.getUserName());
            orderInfoDto.setUserId(resOrderUser.getUserId());
            rows = experimentOrderMapper.updateBack(orderInfoDto);
            //删除上一个操作者
            updateOrderUserMapper.updateUpdateOrderUser(updateOrderUser);
        }
        return rows;
    }
}
