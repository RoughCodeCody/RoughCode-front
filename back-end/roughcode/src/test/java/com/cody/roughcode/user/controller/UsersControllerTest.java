package com.cody.roughcode.user.controller;

import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.service.UsersServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController target;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }

    private Long id = 1L;
    private String email = "cody306@gmail.com";
    private String nickname = "코디";
    private List<String> roles = List.of(String.valueOf(ROLE_USER));

    final Users users = Users.builder()
            .usersId(id)
            .email(email)
            .name(nickname)
            .roles(roles)
            .build();

    @Mock
    private UsersServiceImpl usersService;
    String newNickname = "코디";

    @DisplayName("회원 정보 조회 성공")
    @Test
    public void selectOneUserSucceed() throws Exception {
        final String url = "/api/v1/user";
    }

    @DisplayName("회원 정보 조회 성공")
    @Test
    public void updateUserSucceed() throws Exception {
        final String url = "/api/v1/user";
    }

    @DisplayName("회원 정보 조회 성공")
    @Test
    public void checkNicknameSucceed() throws Exception {
        final String url = "/api/v1/user";
    }
}