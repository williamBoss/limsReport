package com.lims.common.utils;

import com.lims.project.mail.domain.MailDTO;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件处理类
 *
 * @author jiafuwei
 */
public class MailUtil {

    /*
     * private static final String FROM_MAIL_SMTP = "email.simceredx.com"; //
     * private static final String FROM_MAIL_SMTP = "email.simcere.com"; private
     * static final String FROM_MAIL_NAME = "lims@simceredx.com"; private static
     * final String FROM_MAIL_PASS = "limslims";
     */

    public static boolean sendMail(MailDTO mail) {
        return sendMail(mail.getToArray(), mail.getCcArray(), mail.getSubject(), mail.getContent(),
            mail.getFileSrcArray(), mail.getFileNameArray(), mail.getHost(), mail.getAccount(), mail.getPassword());
    }

    /**
     * 发送邮件(灵活度高，通用版)
     *
     * @param to       收件人, 多个Email以英文逗号分隔
     * @param cc       抄送, 多个Email以英文逗号分隔
     * @param subject  主题
     * @param content  内容
     * @param fileList 附件列表
     * @return
     */
    private static boolean sendMail(String[] to, String[] cc, String subject, String content, String[] fileList,
        String[] fileDownloadName, String host, String account, String password) {
        if (fileList != null) {
            if (fileDownloadName == null || fileList.length != fileDownloadName.length) {
                return false;
            }
        }
        if (to == null || to.length == 0) {
            return false;
        }
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            final Properties p = System.getProperties();
            p.setProperty("mail.smtp.host", host);
            p.setProperty("mail.smtp.auth", "true");
            p.setProperty("mail.smtp.user", account);
            p.setProperty("mail.smtp.pass", password);
            p.setProperty("mail.mime.splitlongparameters", "false");

            p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            // true 打开 465 安全端口
            p.setProperty("mail.smtp.socketFactory.fallback", "true");
            // 邮箱发送服务器端口,这里设置为465端口
            p.setProperty("mail.smtp.port", "465");
            p.setProperty("mail.smtp.socketFactory.port", "465");

            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session session = Session.getInstance(p, new Authenticator() {
                @Override protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(p.getProperty("mail.smtp.user"), p.getProperty("mail.smtp.pass"));
                }
            });
            //session.setDebug(true);
            Message message = new MimeMessage(session);
            // 消息发送的主题
            message.setSubject(subject);
            // 接受消息的人
            message.setReplyTo(InternetAddress.parse(account));
            // 消息的发送者
            message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), "消息通知"));
            // 创建邮件的接收者地址，并设置到邮件消息中
            // String[] split = to.split(",");
            InternetAddress[] tos = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                tos[i] = new InternetAddress(to[i]);
            }
            // 设置抄送人
            if (cc != null && cc.length > 0) {
                InternetAddress[] ccs = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; i++) {
                    ccs[i] = new InternetAddress(cc[i]);
                }
                message.setRecipients(Message.RecipientType.CC, ccs);
            }
            message.setRecipients(Message.RecipientType.TO, tos);
            // 消息发送的时间
            message.setSentDate(new Date());
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 设置附件
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    html = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(fileList[i]);
                    html.setDataHandler(new DataHandler(fds));
                    html.setFileName(MimeUtility.encodeText(fileDownloadName[i], "UTF-8", null));
                    // System.out.println(html.getFileName());
                    mainPart.addBodyPart(html);
                }
            }
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(mainPart);
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 测试Mian方法
     *
     * @param args
     */
    public static void main(String[] args) {
        String content =
            "<html><head><style type=\"text/css\"> p { padding-left: 50px; } table { padding-left: 50px;border: 0; }</style></head><body> 尊敬的客户您好：<br /><p> 感谢您选择先声诊断。附件是患者姓名-YYYYMMDD-检测项目名称的检测报告，敬请查收。</p> 祝好！</body></html>";
        System.out.println(content);
        String[] fileList = new String[1];
        fileList[0] = "C:\\Users\\kingz\\Desktop\\病理评估报告模板_v1.pdf";
        String[] names = new String[] {"质控风险告知书 j015 j015 泛癌种 25 基因化疗 病理评估报告模板.pdf"};
        String[] cc = {};
        String[] to = {"kingzerobossm@outlook.com","kingzeroboss@gmail.com"};
        MailDTO mail = new MailDTO();
        mail.setCcArray(cc);
        mail.setToArray(to);
        mail.setSubject("测试");
        mail.setContent(content);
        mail.setFileNameArray(names);
        mail.setFileSrcArray(fileList);
        MailUtil.sendMail(mail.getToArray(), mail.getCcArray(), mail.getSubject(), mail.getContent(),
            mail.getFileSrcArray(), mail.getFileNameArray(), mail.getHost(), mail.getAccount(), mail.getPassword());
        // sendMail("362950217@qq.com", "hongwu.lin@simceredx.com", "测试测试",
    }
}