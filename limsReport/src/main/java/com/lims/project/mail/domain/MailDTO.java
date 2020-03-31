package com.lims.project.mail.domain;

/**
 * @author KING
 * @version V1.0
 * @Title: Mail
 * @Package com.lims.project.mail.domain
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/16 17:13
 */
public class MailDTO {

    private String[] toArray;
    private String[] ccArray;
    private String subject;
    private String content;
    private String[] fileSrcArray;
    private String[] fileNameArray;
    private String host;
    private String account;
    private String password;

    public String[] getToArray() {
        return toArray;
    }

    public void setToArray(String[] toArray) {
        this.toArray = toArray;
    }

    public String[] getCcArray() {
        return ccArray;
    }

    public void setCcArray(String[] ccArray) {
        this.ccArray = ccArray;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getFileSrcArray() {
        return fileSrcArray;
    }

    public void setFileSrcArray(String[] fileSrcArray) {
        this.fileSrcArray = fileSrcArray;
    }

    public String[] getFileNameArray() {
        return fileNameArray;
    }

    public void setFileNameArray(String[] fileNameArray) {
        this.fileNameArray = fileNameArray;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
