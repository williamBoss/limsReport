package com.lims.common.constant;

/**
 * 订单常量
 */
public class ExperimentOrderConstants {

    public static final String STATUS = "0";
    //
    public static final String STATUSYSH = "4";

    //获取订单列表的接口地址
    public static final String LIMS_ORDER_LIST_URL = "http://30.6.0.13:5009/experimentOrder/list";

    //获取实验单实验结果详情信息接口地址
    public static final String LIMS_ORDER_INFO_URL = "http://30.6.0.13:5009/experimentResult/info";
    //订单详情地址 details
    public static final String LIMS_ORDER_DETAILS_URL = "http://30.6.0.13:5009/experimentOrder/details";

    //前端页面传过来的标示  待分配
    public static final String DFP = "DFP";
    //前端页面传过来的标示  已上传
    public static final String YSC = "YSC";
    //前端页面传过来的标示  待审核
    public static final String DSH = "DSH";
    //前端页面传过来的标示  待审核
    public static final String YFS = "YFS";

    //前端页面传过来的标示  已分配
    public static final String YFP = "YFP";
    //操作状态  0 代表审核 1 代表回退
    public static final String OPERATIONTYPE = "1";

}
