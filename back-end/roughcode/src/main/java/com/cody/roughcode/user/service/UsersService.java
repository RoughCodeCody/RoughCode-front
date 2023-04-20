package com.cody.roughcode.user.service;

import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.entity.Users;

public interface UsersService {

    UserResp selectOneUser(Long userId);

    default UserResp toDto(Users user) {
        return UserResp.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
