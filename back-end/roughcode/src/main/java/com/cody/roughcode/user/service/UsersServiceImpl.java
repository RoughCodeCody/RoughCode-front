package com.cody.roughcode.user.service;

import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public UserResp selectOneUser(Long userId) {
        return toDto(usersRepository.findById(userId).orElseThrow());
    }
}
