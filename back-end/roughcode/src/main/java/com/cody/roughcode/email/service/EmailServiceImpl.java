package com.cody.roughcode.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl extends EmailService {

    private final JavaMailSender mailSender;
    private static final int CODE_LENGTH = 8;
    static String code;

    @Value("${spring.mail.username}")
    String from;

    @Override
    public String sendCertificationEmail(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("개발새발 이메일 인증 코드");
        helper.setText(createCertificationEmail(), true); // true를 전달하여 HTML을 사용하도록 지정합니다.

        mailSender.send(message);

        return code;
    }

    private String createCertificationEmail() {
        StringBuilder message = new StringBuilder();
        code = generateVerificationCode();
        message.append("<div style=\"display:flex; justify-content:center;\">\n" +
                "    <table summary=\"인증 메일\" cellpadding=\"0\" cellspacing=\"0\" width=\"70%\" border=\"0\" style=\"border-collapse: collapse;\">\n" +
                "    <tbody><tr><td width=\"100%\" style=\"font-size: 0;line-height: 0;\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-collapse: collapse;\">\n" +
                "                <tbody><tr><td width=\"520\" height=\"50\" style=\"font-size: 0;line-height: 0;\"></td></tr> \n" +
                "                <tr><td width=\"520\" style=\"font-size: 0;\"><div style=\"display: flex; align-items: center;\">\n" +
                "                <img src=\"https://roughcode.s3.ap-northeast-2.amazonaws.com/foots-color.jpg\" alt=\"로고 이미지\" width=\"148\" border=\"0\" style=\"display: block; margin: 0;\" loading=\"lazy\">\n" +
                "                <h1 style=\"font-family: 'Nanum Gothic','Malgun Gothic', 'dotum','AppleGothic', Helvetica, Arial, Sans-Serif;font-size: 22px;line-height: 1.3;letter-spacing: -1px; margin-left: 10px;\">인증메일</h1>\n" +
                "                </div>\n" +
                "                </td></tr> " +
                "                <tr><td width=\"100%\" height=\"25\" style=\"font-size: 0;line-height: 0;border-bottom: 2px solid #22543D;\"></td></tr> \n" +
                "                <tr><td width=\"520\" height=\"50\" style=\"font-size: 0;line-height: 0;\"></td></tr> \n" +
                "                <tr><td width=\"520\" style=\"font-size: 0;\">\n" +
                "                        <span style=\"font-family: 'Nanum Gothic','Malgun Gothic', 'dotum','AppleGothic', Helvetica, Arial, Sans-Serif;font-size: 16px;line-height: 1.6;letter-spacing: -1px;\"><b style=\"color: #319795; \">개발새발</b> 사이트 알림을 위한 인증번호 발송 메일입니다.<br><br>아래의 인증번호를 사용하여 이메일 주소 인증을 완료하면<br>다음 단계로 진행이 가능합니다.</span>\n" +
                "                    </td></tr>\n" +
                "                <tr><td width=\"520\" height=\"50\" style=\"font-size: 0;line-height: 0;\"></td></tr> \n" +
                "                <tr><td align=\"center\" width=\"600\" height=\"50\" colspan=\"3\" bgcolor=\"#E2E8F0\" style=\"font-family: 'Nanum Gothic','Malgun Gothic', 'dotum','AppleGothic', Helvetica, Arial, Sans-Serif;font-size: 22px;line-height: 1.6;letter-spacing: -1px;font-weight:bold\">")
                .append(code)
                .append("</td></tr> \n" +
                "                <tr><td width=\"520\" height=\"50\" style=\"font-size: 0;line-height: 0;\"></td></tr> \n" +
                "            </tbody></table>\n" +
                "        </td></tr>\n" +
                "</tbody></table>\n" +
                "<p><img alt=\"\" src=\"https://ems.univ.me/tm6/app/response/open/automail/61dae90b4e9dbea08c0bfb8f506ff16f/add1bac5f3f5d159ed0950263c092bed4c39a6f10624051bdd3d26f6b65a4bdb\" width=\"0\" height=\"0\" style=\"display:none\" loading=\"lazy\"></p>\n" +
                "\n" +
                "</div>");

        return message.toString();
    }

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < 8; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

}