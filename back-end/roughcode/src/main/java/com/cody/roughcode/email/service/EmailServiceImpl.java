package com.cody.roughcode.email.service;

import com.cody.roughcode.alarm.dto.req.AlarmReq;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final UsersRepository usersRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    int CERTIFICATE_TIME = 3 * 1000 * 60; // 3분
    private final JavaMailSender mailSender;
    private static final int CODE_LENGTH = 8;
    static String code;

    @Value("${spring.mail.username}")
    String from;

    @Override
    public void sendCertificationEmail(String to, Long usersId) throws MessagingException {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("[🐾개발새발🐾]개발새발 이메일 인증 코드");
        helper.setText(createCertificationEmail(), true); // true를 전달하여 HTML을 사용하도록 지정합니다.

        mailSender.send(message);

        redisTemplate.opsForValue()
                .set(to, code, CERTIFICATE_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean checkEmail(String to, String code, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        String value = (String) redisTemplate.opsForValue().get(to);
        if(value == null) throw new NullPointerException("인증코드가 만료되었습니다");

        boolean checked = value.equals(code);
        if(checked) {
            user.setEmail(to);
            usersRepository.save(user);
        };
        return checked;
    }

    @Override
    public String deleteEmailInfo(Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");

        user.setEmail("");
        usersRepository.save(user);

        return user.getEmail();
    }

    @Override
    public void sendAlarm(String subject, AlarmReq alarmReq) throws MessagingException {
        Users user = usersRepository.findByUsersId(alarmReq.getUserId());
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        if(user.getEmail() == null || user.getEmail().equals("")) {
            log.debug(user.getName() + "은 이메일이 등록되어있지 않습니다");
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(user.getEmail());
        helper.setSubject("[🐾개발새발🐾]" + subject);
        helper.setText(createAlarmEmail(alarmReq), true); // true를 전달하여 HTML을 사용하도록 지정합니다.

        mailSender.send(message);
    }

    private String createAlarmEmail(AlarmReq alarm) {
        StringBuilder message = new StringBuilder();
        message.append("<div style=\"display:flex\">\n" +
                "    <table summary=\"알림 메일\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td width=\"100%\">\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td width=\"520\" height=\"50\"></td>\n" +
                "                            </tr> \n" +
                "                            <tr>\n" +
                "                                <td width=\"520\" style=\"font-size:0;\">\n" +
                "                                    <div style=\"display:flex;\">\n" +
                "                                        <img src=\"https://d2swdwg2kwda2j.cloudfront.net/dog_and_dock-full_body-mini.png\" alt=\"로고 이미지\" height=\"80\">\n" +
                "                                        <h1 style=\"font-family:'Nanum Gothic','Malgun Gothic','dotum','AppleGothic',Helvetica,Arial,Sans-Serif;font-size:22px;line-height:3;letter-spacing:-1px;margin-left:2em;color:black;\">개발새발에서 확인해보세요</h1>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td width=\"100%\" height=\"25\" style=\"font-size:0;line-height:0;border-bottom:2px solid #22543d\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding-top: 3em;padding-left: 3em;\">\n" +
                "                                    <span style=\"font-family:'Nanum Gothic','Malgun Gothic','dotum','AppleGothic',Helvetica,Arial,Sans-Serif;font-size:18px;letter-spacing:-1px;margin-left:10px\">")
                .append(alarm.getContent().get(0) + "</span>\n")
                .append("                                    <a  style=\"font-family:'Nanum Gothic','Malgun Gothic','dotum','AppleGothic',Helvetica,Arial,Sans-Serif;font-size:22px; font-weight:bold;letter-spacing:-1px;margin-left:10px;text-decoration:none;color:#319795; border-bottom: 1px solid transparent;\" href=\"https://rough-code.com/")
                .append(alarm.getSection() + "/" + alarm.getPostId())
                .append("\" target=\"_blank\" >\n")
                .append(alarm.getContent().get(1))
                .append("                                    </a>\n" +
                "                                    <span style=\"font-family:'Nanum Gothic','Malgun Gothic','dotum','AppleGothic',Helvetica,Arial,Sans-Serif;font-size:18px;letter-spacing:-1px;margin-left:10px\">")
                .append(alarm.getContent().get(2) + "</span> \n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "\n" +
                "<style>\n" +
                "    a:hover {\n" +
                "        border-bottom: 1px solid #319795;\n" +
                "    }\n" +
                "</style>\n");

        return message.toString();
    }

    private String createCertificationEmail() {
        StringBuilder message = new StringBuilder();
        code = generateVerificationCode();
        message.append("<div style=\"display:flex; justify-content:center;\">\n" +
                "    <table summary=\"인증 메일\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-collapse: collapse;\">\n" +
                "    <tbody><tr><td width=\"100%\" style=\"font-size: 0;line-height: 0;\">\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-collapse: collapse;\">\n" +
                "                <tbody><tr><td width=\"520\" height=\"50\" style=\"font-size: 0;line-height: 0;\"></td></tr> \n" +
                "                <tr><td width=\"520\" style=\"font-size: 0;\"><div style=\"display: flex; align-items: center;\">\n" +
                "                <img src=\"https://d2swdwg2kwda2j.cloudfront.net/foots-color-mini.jpg\" alt=\"로고 이미지\" width=\"148\" border=\"0\" style=\"display: block; margin: 0;\" loading=\"lazy\">\n" +
                "                <h1 style=\"font-family: 'Nanum Gothic','Malgun Gothic', 'dotum','AppleGothic', Helvetica, Arial, Sans-Serif;font-size: 22px;line-height:3;letter-spacing: -1px; margin-left: 10px;color:black;\">인증메일</h1>\n" +
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