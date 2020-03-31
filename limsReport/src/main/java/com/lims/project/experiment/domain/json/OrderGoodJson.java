package com.lims.project.experiment.domain.json;

import java.util.List;

/**
 * 检测订单详情寄送数据
 * Created by Administrator on 2020/3/20.
 */
public class OrderGoodJson {

    /** 订单编号 */
    private String  orderCode;
    /** 商品编码 */
    private String  goodsCode;

    /** 检测项目 */
    private String   goodsName;

    /** 实验单号 */
    private String    experimentCode;

    /**检测完成时间*/
    public String updateTime;

    private List<OriginSamplesJson> originSamples;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<OriginSamplesJson> getOriginSamples() {
        return originSamples;
    }

    public void setOriginSamples(List<OriginSamplesJson> originSamples) {
        this.originSamples = originSamples;
    }

    @Override
    public String toString() {
        return "OrderGoodJson{" +
                "orderCode='" + orderCode + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", experimentCode='" + experimentCode + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", originSamples=" + originSamples +
                '}';
    }
}
