package com.cody.roughcode.email.service;

import javax.mail.MessagingException;

public abstract class EmailService {
    public abstract void sendCertificationEmail(String to, Long usersId) throws MessagingException;
    public abstract boolean checkEmail(String to, String code, Long usersId);
}
