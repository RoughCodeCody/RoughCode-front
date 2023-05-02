package com.cody.roughcode.user.service;

import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserRes;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class) // 가짜 객체 주입을 사용
public class UserServiceTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;

    private Long id = 1L;
    private String email = "cody306@gmail.com";
    private String nickname = "코디";
    private List<String> roles = List.of(String.valueOf(ROLE_USER));

    @DisplayName("회원 정보 조회 성공")
    @Test
    void selectOneUserSucceed() throws Exception {
        // given
        Users users = Users.builder()
                .usersId(id)
                .email(email)
                .name(nickname)
                .roles(roles)
                .build();
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when
        UserRes user = usersService.selectOneUser(id);

        // then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
    }

    @DisplayName("회원 정보 조회 실패 - 일치하는 회원 정보 없음")
    @Test
    void selectOneUserFailNoUser() throws Exception {
        // given
        Users users = Users.builder()
                .usersId(id)
                .email(email)
                .name(nickname)
                .roles(roles)
                .build();

        // when & then
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> usersService.selectOneUser(users.getUsersId())
        );
        assertEquals("일치하는 유저가 존재하지 않습니다.", exception.getMessage());
    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    void updateUserSucceed() throws Exception {
        String newNickname = "코디코디";
        String newEmail = "cody306@roughcode.com";

        // given
        UserReq userReq = UserReq.builder()
                .nickname(newNickname)
                .email(newEmail)
                .build();

        Users users = Users.builder()
                .usersId(id)
                .email(email)
                .name(nickname)
                .roles(roles)
                .build();
        doReturn(users).when(usersRepository).findByUsersId(any(Long.class));

        // when
        usersService.updateUser(users.getUsersId(), userReq);

        UserRes updatedUser = usersService.selectOneUser(users.getUsersId());

        // then
        assertThat(updatedUser.getEmail()).isEqualTo(newEmail);
        assertThat(updatedUser.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("회원 정보 수정 실패 - 회원 정보가 없는 경우")
    @Test
    void updateUserFail() throws Exception {
        String newNickname = "코디코디";
        String newEmail = "cody306@roughcode.com";

        // given
        UserReq userReq = UserReq.builder()
                .nickname(newNickname)
                .email(newEmail)
                .build();

        Users users = Users.builder()
                .usersId(id)
                .email(email)
                .name(nickname)
                .roles(roles)
                .build();

        // when & then
        doReturn(null).when(usersRepository).findByUsersId(any(Long.class));
        NullPointerException exception = assertThrows(
                NullPointerException.class, () -> usersService.updateUser(users.getUsersId(), userReq)
        );

        // then
        assertEquals("일치하는 유저가 존재하지 않습니다.", exception.getMessage());
    }

    @DisplayName("닉네임 중복 체크 - 닉네임이 이미 존재하는 경우")
    @Test
    void nicknameDuplicatedTrue() throws Exception {
        String newNickname = "코디";

        // given
        doReturn(true).when(usersRepository).existsByName(any(String.class));

        // when
        boolean duplicated = usersService.checkNickname(newNickname);

        // then
        assertThat(duplicated).isEqualTo(true);
    }
}
