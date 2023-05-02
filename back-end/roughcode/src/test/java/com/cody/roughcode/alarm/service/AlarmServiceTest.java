package com.cody.roughcode.alarm.service;

import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.entity.AlarmRes;
import com.cody.roughcode.alarm.repository.AlarmRepository;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AlarmServiceTest {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    @InjectMocks
    private AlarmServiceImpl alarmService;

    @Mock
    private AlarmRepository alarmRepository;
    @Mock
    private UsersRepository usersRepository;

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("kosy318")
            .roles(List.of(String.valueOf(String.valueOf(ROLE_USER))))
            .build();

    final Alarm alarm1 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(1L)
            .id(new ObjectId("60a957bcf77ec21e1aa72c93"))
            .section("project")
            .content(List.of("test", "project1", "test"))
            .userId(1L)
            .build();
    final Alarm alarm2 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(2L)
            .id(new ObjectId("60a957bcf77ec21e1aa72c93"))
            .section("project")
            .content(List.of("test", "project2", "test"))
            .userId(1L)
            .build();

    final Alarm alarm3 = Alarm.builder()
            .createdDate(LocalDateTime.now())
            .postId(1L)
            .id(new ObjectId("60a957bcf77ec21e1aa72c93"))
            .section("project")
            .content(List.of("test", "project1", "test"))
            .userId(2L)
            .build();

    @DisplayName("알람 삭제 성공")
    @Test
    void deleteAlarmSucceed(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(alarm1).when(alarmRepository).findById(any(ObjectId.class));

        // when
        alarmService.deleteAlarm("60a957bcf77ec21e1aa72c93", 1L);
     }

    @DisplayName("알람 삭제 실패 - 유저 존재하지 않음")
    @Test
    void deleteAlarmFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> alarmService.deleteAlarm("60a957bcf77ec21e1aa72c93", 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }

    @DisplayName("알람 삭제 실패 - 해당 알람이 유저한테 속하지 않음")
    @Test
    void deleteAlarmFailNotMatch(){
        // given
        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(alarm3).when(alarmRepository).findById(any(ObjectId.class));

        // when & then
        NotMatchException exception = assertThrows(
                NotMatchException.class, () -> alarmService.deleteAlarm("60a957bcf77ec21e1aa72c93", 1L)
        );
        assertThat(exception.getMessage()).isEqualTo("접근 권한이 없습니다");
    }

    @DisplayName("알람 목록 조회 성공")
    @Test
    void getAlarmListSucceed(){
        // given
        List<Alarm> alarmList = List.of(alarm1, alarm2);

        doReturn(users).when(usersRepository).findByUsersId(1L);
        doReturn(alarmList).when(alarmRepository).findByUserIdOrderByCreatedDateDesc(1L);

        // when
        List<AlarmRes> res = alarmService.getAlarmList(1L);

        // then
        assertThat(res.size()).isEqualTo(2);
    }

    @DisplayName("알람 목록 조회 실패 - 유저 존재하지 않음")
    @Test
    void getAlarmListFailNoUser(){
        // given
        doReturn(null).when(usersRepository).findByUsersId(1L);

        // when & then
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> alarmService.getAlarmList(1L)
        );
        assertThat(exception.getMessage()).isEqualTo("일치하는 유저가 존재하지 않습니다");
    }
}
