package com.lims.project.mail.service;

/**
 * @author KING
 * @version V1.0
 * @Title: SendMailService
 * @Package com.lims.project.mail
 * @Description: 发送邮件(这里用一句话描述这个类的作用)
 * @date 2020/3/16 17:40
 */
public interface SendMailService {

    public boolean sendMail(Long experimentOrderId);

}
