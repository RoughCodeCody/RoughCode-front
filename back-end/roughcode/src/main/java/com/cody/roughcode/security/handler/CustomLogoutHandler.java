package com.cody.roughcode.security.handler;

import com.cody.roughcode.exception.NoTokenException;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.dto.TokenReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //우선 request에 있는 토큰 정보꺼내기
        TokenReq tokenReq = jwtTokenProvider.getToken(request);
        Long userId = jwtTokenProvider.getId(tokenReq.getAccessToken());
        //redis 에 해당 정보로 저장된 Refresh token 이 있는지 여부를 확인후 있다면 삭제
        //없다면 Exception 보내기
        if (!Boolean.TRUE.equals(redisTemplate.delete(userId.toString()))) {
            throw new NoTokenException();
        }
    }
}
