package com.cody.roughcode.email.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;

import javax.mail.MessagingException;

public abstract class EmailService {
    public abstract void sendCertificationEmail(String to, Long usersId) throws MessagingException;
    public abstract boolean checkEmail(String to, String code, Long usersId);
    public abstract void sendAlarm(String subject, AlarmReq alarmReq) throws MessagingException;
}
