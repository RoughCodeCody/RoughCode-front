package com.cody.roughcode.email.service;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ValueOperations operations;

    @Mock
    private JavaMailSender mailSender;

    private String from = "from@from.email";


    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    @Test
    @DisplayName("알람전송 테스트")
    void sendAlarmSucceed() throws MessagingException {
        // given
        emailService.from = "test@test.com";

        MimeMessage message = mock(MimeMessage.class);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        when(usersRepository.findByUsersId(1L)).thenReturn(users);
        when(mailSender.createMimeMessage()).thenReturn(message);

        // when
        emailService.sendAlarm("alarm 제목", "alarm 내용", 1L);

        // then
        verify(mailSender, times(1)).send(message);
    }

    @Test
    @DisplayName("이메일 인증 코드 전송 테스트")
    void sendCertificationEmail() throws MessagingException {
        // given
        String to = "example@example.com";
        String code = emailService.generateVerificationCode();
        emailService.from = "test@test.com";

        MimeMessage message = mock(MimeMessage.class);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        when(usersRepository.findByUsersId(1L)).thenReturn(users);
        when(mailSender.createMimeMessage()).thenReturn(message);
        when(redisTemplate.opsForValue()).thenReturn(operations);

        // when
        emailService.sendCertificationEmail(to, 1L);

        // then
        verify(mailSender, times(1)).send(message);
        verify(operations, times(1)).set(eq(to), any(String.class), eq((long)emailService.CERTIFICATE_TIME), eq(TimeUnit.MILLISECONDS));
    }

    @Test
    @DisplayName("이메일 인증 코드 검증 테스트")
    void checkEmail() {
        // given
        String to = "example@example.com";
        String code = "12341234";
        when(usersRepository.findByUsersId(1L)).thenReturn(users);
        when(redisTemplate.opsForValue()).thenReturn(operations);
        when(operations.get(eq(to))).thenReturn(code);

        // when
        boolean check1 = emailService.checkEmail(to, code, 1L);
        boolean check2 = emailService.checkEmail(to, "12345678", 1L);

        // then
        assertThat(check1).isTrue();
        assertThat(check2).isFalse();
    }

    @Test
    @DisplayName("인증코드 생성 테스트")
    void generateVerificationCode() {
        String code = emailService.generateVerificationCode();
        assertTrue(code.matches("[0-9a-zA-Z]{8}"));
    }

}
