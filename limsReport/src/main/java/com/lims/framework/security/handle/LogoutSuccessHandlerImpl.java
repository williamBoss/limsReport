package com.lims.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.lims.common.constant.Constants;
import com.lims.common.constant.HttpStatus;
import com.lims.common.utils.ServletUtils;
import com.lims.common.utils.StringUtils;
import com.lims.common.utils.text.LoginUtils;
import com.lims.framework.manager.AsyncManager;
import com.lims.framework.manager.factory.AsyncFactory;
import com.lims.framework.security.LoginUser;
import com.lims.framework.security.service.TokenService;
import com.lims.framework.web.domain.AjaxResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义退出处理类 返回成功
 *
 * @author lims
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute(LoginUtils.ACCOUNT);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            LoginUtils.removeSession();
            // tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
