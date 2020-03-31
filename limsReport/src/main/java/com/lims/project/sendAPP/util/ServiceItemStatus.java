package com.lims.project.sendAPP.util;

/**
 * @author KING
 * @version V1.0
 * @Title: ServiceItemStatus
 * @Package com.lims.project.sendAPP.util
 * @Description: app推送状态(这里用一句话描述这个类的作用)
 * @date 2020/3/24 15:57
 */

public enum ServiceItemStatus {
    /**
     * 样本寄出
     */
    STATUS_SAMPLE_SEND(1),
    /**
     * 样本接收
     */
    STATUS_SAMPLE_RECEIVE(2),
    /**
     * 样本检测中
     */
    STATUS_SAMPLE_TESTING(3),
    /**
     * 报告审核中
     */
    STATUS_REPORT_REVIEW(4),
    /**
     * 报告已生成
     */
    STATUS_REPORT_GENERATE(5),
    /**
     * 停止检测
     */
    STATUS_STOP(6);

    private final int value;

    private ServiceItemStatus(int value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     *
     * @return null if the value is not found.
     */
    public static ServiceItemStatus findByValue(int value) {
        switch (value) {
            case 1:
                return STATUS_SAMPLE_SEND;
            case 2:
                return STATUS_SAMPLE_RECEIVE;
            case 3:
                return STATUS_SAMPLE_TESTING;
            case 4:
                return STATUS_REPORT_REVIEW;
            case 5:
                return STATUS_REPORT_GENERATE;
            case 6:
                return STATUS_STOP;
            default:
                return null;
        }
    }
}
