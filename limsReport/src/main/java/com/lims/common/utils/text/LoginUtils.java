package com.lims.common.utils.text;

import static com.lims.common.utils.ServletUtils.getRequest;

import com.lims.common.constant.UserConstants;
import com.lims.framework.security.LoginUser;
import com.lims.project.system.domain.SysUser;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2020/3/16.
 */
public class LoginUtils {

    /**
     * 当前账号常量
     */
    public static final String ACCOUNT = "user";

    public static LoginUser getAccount() {
        HttpSession session = getRequest().getSession();
        return (LoginUser) session.getAttribute(ACCOUNT);
    }

    public static SysUser getSessionUserInfo() {
        return getAccount().getUser();
    }

    public static void setAccount(LoginUser user) {
        HttpSession session = getRequest().getSession();
        if (user != null) {
            session.setAttribute(ACCOUNT, user);
            //session过期时间设置，以秒为单位，即在没有活动30分钟后，session将失效
            session.setMaxInactiveInterval(3600);
        }
    }

    /**
     * 删除session
     */
    public static String removeSession() {
        HttpSession session = getRequest().getSession();
        LoginUser user = getAccount();
        if (user == null) {
            return UserConstants.NOT_UNIQUE;
        } else {
            session.removeAttribute(ACCOUNT);
            return UserConstants.UNIQUE;
        }

    }
}
