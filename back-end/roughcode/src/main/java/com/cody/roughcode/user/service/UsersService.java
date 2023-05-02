package com.cody.roughcode.user.service;

import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserRes;
import com.cody.roughcode.user.entity.Users;

public interface UsersService {

    UserRes selectOneUser(Long userId);

    void updateUser(Long userId, UserReq req);

    boolean checkNickname(String nickname);

    default UserRes toDto(Users user) {
        return UserRes.builder()
                .nickname(user.getName())
                .email(user.getEmail())
                .projectsCnt(user.getProjectsCnt())
                .codesCnt(user.getCodesCnt())
                .build();
    }

}
