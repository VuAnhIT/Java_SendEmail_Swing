package com.theanh.entity;

/**
 * Created by Vu The Anh  - vutheanhit@gmail.com
 * On 07/05/2019
 * This is the class that contain data of a mail message
 */
public class MailMessage {
    private String to;
    private String from;
    private String message;
    private String subject;

    public MailMessage(String to, String from, String message, String subject) {
        this.to = to;
        this.from = from;
        this.message = message;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
