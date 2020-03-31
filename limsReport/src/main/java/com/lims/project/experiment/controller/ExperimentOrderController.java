package com.lims.project.experiment.controller;

import com.lims.common.constant.ExperimentOrderConstants;
import com.lims.common.utils.StringUtils;
import com.lims.common.utils.poi.ExcelUtil;
import com.lims.framework.aspectj.lang.annotation.Log;
import com.lims.framework.aspectj.lang.enums.BusinessType;
import com.lims.framework.config.RuoYiConfig;
import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.framework.web.page.TableDataInfo;
import com.lims.project.experiment.domain.ExperimentOrder;
import com.lims.project.experiment.domain.ExperimentOrderVo;
import com.lims.project.experiment.domain.OrderInfoDto;
import com.lims.project.experiment.service.IExperimentOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 检测订单Controller
 *
 * @author yanglin
 * @date 2020-03-12
 */
@Api("订单信息管理")
@RestController
@RequestMapping("/experiment/order")
public class ExperimentOrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(ExperimentOrderController.class);
    @Autowired
    private IExperimentOrderService experimentOrderService;

    /**
     * 查询检测订单列表
     */
    @ApiOperation("查询检测订单列表")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "orderCode", value = "订单编号", paramType = "query", required = false),
             @ApiImplicitParam(name = "goodsName", value = "检测项目", paramType = "query", required = false),
             @ApiImplicitParam(name = "patientName", value = "患者姓名", paramType = "query", required = false),
            @ApiImplicitParam(name = "leaderStatus", value = "状态", paramType = "query", required = false),
            @ApiImplicitParam(name = "orderSort", value = "排序", paramType = "query", required = false),
            @ApiImplicitParam(name = "salerName", value = "销售", paramType = "query", required = false),
            @ApiImplicitParam(name = "hospitalName", value = "医院", paramType = "query", required = false),
            @ApiImplicitParam(name = "start", value = "开始时间", paramType = "query", required = false),
            @ApiImplicitParam(name = "end", value = "结束时间", paramType = "query", required = false),
            @ApiImplicitParam(name = "identification", value = "页面标识", paramType = "query", required = false),

    })
    @GetMapping("/list")
    public TableDataInfo list(String goodsName, String orderCode, String patientName, String leaderStatus,
        Integer orderSort, String salerName,String hospitalName,String identification,String start,String end) {
        List<ExperimentOrderVo> list=null;
        TableDataInfo tableDataInfo=new TableDataInfo();
        try {
            OrderInfoDto orderInfoDto = new OrderInfoDto();
            orderInfoDto.setGoodsName(goodsName);
            orderInfoDto.setOrderSort(orderSort);
            orderInfoDto.setOrderCode(orderCode);
            orderInfoDto.setPatientName(patientName);
            orderInfoDto.setLeaderStatus(leaderStatus);
            orderInfoDto.setSalerName(salerName);
            orderInfoDto.setHospitalName(hospitalName);
            orderInfoDto.setIdentification(identification);
            orderInfoDto.setStart(start);
            orderInfoDto.setEnd(end);
            startPage();
            list = experimentOrderService.selectExperimentOrderList(orderInfoDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return  null;
        }

        return getDataTable(list);
    }

    /**
     * 导出检测订单列表
     */
    @ApiOperation("导出检测订单列表")
    @Log(title = "检测订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiImplicitParam(name = "experimentOrderIds", value = "订单主键", paramType = "query", required = true)
    public AjaxResult export(HttpServletResponse response,String experimentOrderIds) {
        if(StringUtils.isEmpty(experimentOrderIds)){
           return   AjaxResult.error("请选择导出的数据");
        }
        List<ExperimentOrderVo> list = null;
        ExcelUtil<ExperimentOrderVo> util = null;
        AjaxResult ajaxResult=null;
        try {
            list = experimentOrderService.selectExportList(experimentOrderIds);
            util = new ExcelUtil<ExperimentOrderVo>(ExperimentOrderVo.class);
            ajaxResult=  util.exportExcel(list, "order");
           System.out.println(ajaxResult.get("code").toString());
            System.out.println("200".equals(ajaxResult.get("code").toString()));
           if("200".equals(ajaxResult.get("code").toString())){
               ajaxResult=  fileDownLoad(response,ajaxResult.get("msg").toString());
           }
       } catch (Exception e) {
           AjaxResult.error("导出异常");
           log.error(e.getMessage());
       }
       return  ajaxResult;
    }
    public  AjaxResult fileDownLoad(HttpServletResponse response,String fileName){
        String  downloadPath = RuoYiConfig.getDownloadPath()+fileName;
        File file = new File(
                downloadPath);
        if (!file.exists()) {
            return AjaxResult.error("下载文件不存在");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error("导出失败");
        }
        return AjaxResult.success("导出成功");
    }

    @ApiOperation("获取检测订单详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "experimentOrderId", value = "订单ID", required = true, dataType = "Long", paramType = "path")})
    @GetMapping("/getInfo")
    public AjaxResult getInfo(Long experimentOrderId) {

        System.out.println(experimentOrderId);
        return AjaxResult.success(experimentOrderService.selectExperimentOrderById(experimentOrderId));
    }

    /**
     * 拉取lims的检测订单
     */
    @Log(title = "拉取lims的检测订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(String fromTime, String toTime) {
        return toAjax(experimentOrderService.insertExperimentOrder(fromTime, toTime));
    }
    @ApiOperation("检测订单分配")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "experimentOrderIds", value = "订单ID", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "操作者Id", dataType = "Long", paramType = "query", required = true),
        @ApiImplicitParam(name = "userName", value = "操作者姓名", dataType = "string", paramType = "query", required = true),
        @ApiImplicitParam(name = "leaderStatus", value = "状态", dataType = "string", paramType = "query", required = true),
         @ApiImplicitParam(name = "operationType", value = "操作类型", dataType = "string", paramType = "query", required = true),
    })
    @Log(title = "检测订单", businessType = BusinessType.UPDATE)
    @GetMapping("/edit")
    public AjaxResult edit(String experimentOrderIds, Long userId, String userName, String leaderStatus,String operationType) {
        try {
            if(StringUtils.isEmpty(userName)&&("1".equals(leaderStatus)||"3".equals(leaderStatus))){
                return   AjaxResult.error("用户姓名不能为空");
            }
            if(userId==null&&("1".equals(leaderStatus)||"3".equals(leaderStatus))){
                return AjaxResult.error("用户Id不能为空");
            }
            if(experimentOrderIds==null){
                return AjaxResult.error("订单主键不能为空");
            }
            int rows = experimentOrderService.updateExperimentOrder(experimentOrderIds, userId, userName, leaderStatus,operationType);
            if (rows <= 0) {
                return AjaxResult.error();
            }else if(rows==3){
                return AjaxResult.error("邮件发送失败");
            } else {
                return AjaxResult.success();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error();
        }
    }

    @ApiOperation("获取订单数据测试")
    @GetMapping("/orderList")
    public AjaxResult orderList(String fromTime, String toTime) {
        try {
            experimentOrderService.insertExperimentOrder(fromTime, toTime);
            return AjaxResult.success("查询成功");
        } catch (Exception e) {
            return AjaxResult.error("用户下拉列表查询失败");
        }
    }

    @ApiOperation("app数据保存接口测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderCode", value = "orderCode", required = true, dataType = "string", paramType = "query"),
    })
    @Log(title = "app数据保存接口测试", businessType = BusinessType.UPDATE)
    @GetMapping("/addOriginSamples")
    public AjaxResult addOriginSamples(String orderCode) {
        try {
            OrderInfoDto orderInfoDto=new OrderInfoDto();
            orderInfoDto.setOrderCode(orderCode);
            orderInfoDto.setGoodsCode("333");
            orderInfoDto.setGoodsName("44");
            orderInfoDto.setExperimentOrderId(new Long(1));
            orderInfoDto.setUpdateTime("2020-03-24 15:45");
            experimentOrderService.addOriginSamples(orderInfoDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }
}
