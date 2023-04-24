package com.cody.roughcode.user.service;

import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import com.cody.roughcode.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public UserResp selectOneUser(Long userId) {
        Users user = usersRepository.findByUsersId(userId);

        if(user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다.");
        }

        return toDto(user);
    }

    @Transactional
    @Override
    public void updateUser(Long userId, UserReq req) {

        Users user = usersRepository.findByUsersId(userId);

        if(user == null) {
            throw new NullPointerException("일치하는 유저가 존재하지 않습니다.");
        }

        try {
            // 회원 정보 업데이트 (닉네임, 이메일)
            if(StringUtils.hasText(req.getNickname())){
                System.out.println("회원 정보 수정(닉네임): "+ req.getNickname());
                user.updateName(req.getNickname());
            }
            if(StringUtils.hasText(req.getEmail())){
                System.out.println("회원 정보 수정(이메일): "+ req.getEmail());
                user.updateEmail(req.getEmail());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SaveFailedException(e.getMessage());
        }
    }

    @Override
    public boolean checkNickname(String nickname) {
        return usersRepository.existsByName(nickname);
    }
}
