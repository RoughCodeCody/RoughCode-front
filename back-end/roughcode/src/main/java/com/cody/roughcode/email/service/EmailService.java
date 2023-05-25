package com.cody.roughcode.email.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;

import javax.mail.MessagingException;

public interface EmailService {
    void sendCertificationEmail(String to, Long usersId) throws MessagingException;
    boolean checkEmail(String to, String code, Long usersId);
    String deleteEmailInfo(Long usersId);
    void sendAlarm(String subject, AlarmReq alarmReq) throws MessagingException;
}
