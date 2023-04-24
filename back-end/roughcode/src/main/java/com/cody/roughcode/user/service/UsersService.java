package com.cody.roughcode.user.service;

import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.entity.Users;

public interface UsersService {

    UserResp selectOneUser(Long userId);

    void updateUser(Long userId, UserReq req);

    boolean checkNickname(String nickname);

    default UserResp toDto(Users user) {
        return UserResp.builder()
                .nickname(user.getName())
                .email(user.getEmail())
                .build();
    }

}
