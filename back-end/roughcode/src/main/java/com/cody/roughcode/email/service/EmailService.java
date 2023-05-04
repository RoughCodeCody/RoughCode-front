package com.cody.roughcode.email.service;

import javax.mail.MessagingException;

public abstract class EmailService {
    public abstract String sendCertificationEmail(String to) throws MessagingException;
}
