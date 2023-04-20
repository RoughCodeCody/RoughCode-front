package com.cody.roughcode.user.controller;

import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.service.UsersService;
import com.cody.roughcode.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<?> selectOneUser(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        Long userId = jwtTokenProvider.getId(accessToken);

        UserResp resp = null;
        try{
            resp = usersService.selectOneUser(userId);
        } catch (Exception e){
            log.error(e.getMessage());
        }

//        if(res == 0) return Response.notFound("사용자 정보 조회 실패");
        return Response.makeResponse(HttpStatus.OK, "사용자 정보 조회 성공", 1, resp);
    }

}
