package com.lims.project.experiment.controller;

import com.lims.common.constant.Constants;
import com.lims.common.constant.UserConstants;
import com.lims.common.exception.CustomException;
import com.lims.common.exception.user.UserPasswordNotMatchException;
import com.lims.common.utils.MessageUtils;
import com.lims.common.utils.StringUtils;
import com.lims.common.utils.text.LoginUtils;
import com.lims.framework.manager.AsyncManager;
import com.lims.framework.manager.factory.AsyncFactory;
import com.lims.framework.security.LoginUser;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/3/13.
 */
@Api("用户管理")
@RestController
@RequestMapping("/experiment/user")
public class UserLoginController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private ISysUserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @ApiOperation("用户登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", paramType = "query", required = true),
        @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true),})
    @PostMapping("/login")
    public AjaxResult login(String userName, String password) {
        LoginUser user = null;
        try {
            if (StringUtils.isEmpty(userName)) {
                return AjaxResult.error("用户名不能为空");
            }
            if (StringUtils.isEmpty(password)) {
                return AjaxResult.error("密码不能为空");
            }
            // 用户验证
            Authentication authentication = null;
            try {
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            } catch (Exception e) {
                if (e instanceof BadCredentialsException) {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));
                    throw new UserPasswordNotMatchException();
                } else {
                    AsyncManager.me()
                        .execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_FAIL, e.getMessage()));
                    throw new CustomException(e.getMessage());
                }
            }
            AsyncManager.me().execute(AsyncFactory
                .recordLogininfor(userName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
            user = (LoginUser) authentication.getPrincipal();
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
        //将用户的信息放到session
        LoginUtils.setAccount(user);
        return AjaxResult.success(Constants.TOKEN, user);
    }

    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public AjaxResult Logout() {
        try {
            String result = LoginUtils.removeSession();
            if (result.equals(UserConstants.UNIQUE)) {
                return AjaxResult.success();
            } else {
                return AjaxResult.error("用户退出失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error("用户退出失败");
        }
    }

    @ApiOperation("用户下拉列表查询")
    @GetMapping("/queryUserList")
    public AjaxResult queryUserList() {
        List<Map> list = null;
        try {
            list = userService.queryUserList();
            return AjaxResult.success("查询成功", list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error("用户下拉列表查询失败");
        }
    }
}
