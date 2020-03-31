package com.lims.common.exception.user;

import com.lims.common.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author lims
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
