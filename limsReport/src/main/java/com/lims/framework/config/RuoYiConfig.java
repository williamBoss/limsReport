package com.lims.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author lims
 */
@Component
@ConfigurationProperties(prefix = "lims")
public class RuoYiConfig {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    /**
     * lims请求秘钥
     */
    private static String signKey;

    /**
     * 请求lims的超时时间
     */
    private static Integer ts;

    /**
     * e-mail账户
     */
    private static String emailAccount;

    /**
     * email密码
     */
    private static String emailPassword;

    /**
     * emailHost 要连接的SMTP服务器
     */
    private static String emailHost;

    /**
     * pdf模板路径
     */
    private static String pdfTemplatePath;

    /**
     * 获取订单列表的接口地址
     */
    private static String LIMS_ORDER_LIST_URL;

    /**
     * 获取实验单实验结果详情信息接口地址
     */
    private static String LIMS_ORDER_INFO_URL;

    /**
     * 订单详情地址 details
     */
    private static String LIMS_ORDER_DETAILS_URL;

    private static String appSignKey;

    private static String appSendUrl;

    private static String appSendReportSignKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    public static String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        RuoYiConfig.signKey = signKey;
    }

    public static String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        RuoYiConfig.emailAccount = emailAccount;
    }

    public static String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        RuoYiConfig.emailPassword = emailPassword;
    }

    public static String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        RuoYiConfig.emailHost = emailHost;
    }

    public static Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        RuoYiConfig.ts = ts;
    }

    public static String getPdfTemplatePath() {
        return pdfTemplatePath;
    }

    public void setPdfTemplatePath(String pdfTemplatePath) {
        RuoYiConfig.pdfTemplatePath = pdfTemplatePath;
    }

    public static String getLimsOrderListUrl() {
        return LIMS_ORDER_LIST_URL;
    }

    public void setLimsOrderListUrl(String limsOrderListUrl) {
        LIMS_ORDER_LIST_URL = limsOrderListUrl;
    }

    public static String getLimsOrderInfoUrl() {
        return LIMS_ORDER_INFO_URL;
    }

    public void setLimsOrderInfoUrl(String limsOrderInfoUrl) {
        LIMS_ORDER_INFO_URL = limsOrderInfoUrl;
    }

    public static String getLimsOrderDetailsUrl() {
        return LIMS_ORDER_DETAILS_URL;
    }

    public void setLimsOrderDetailsUrl(String limsOrderDetailsUrl) {
        LIMS_ORDER_DETAILS_URL = limsOrderDetailsUrl;
    }

    public static String getAppSignKey() {
        return appSignKey;
    }

    public void setAppSignKey(String appSignKey) {
        RuoYiConfig.appSignKey = appSignKey;
    }

    public static String getAppSendUrl() {
        return appSendUrl;
    }

    public void setAppSendUrl(String appSendUrl) {
        RuoYiConfig.appSendUrl = appSendUrl;
    }

    public static String getAppSendReportSignKey() {
        return appSendReportSignKey;
    }

    public void setAppSendReportSignKey(String appSendReportSignKey) {
        RuoYiConfig.appSendReportSignKey = appSendReportSignKey;
    }
}