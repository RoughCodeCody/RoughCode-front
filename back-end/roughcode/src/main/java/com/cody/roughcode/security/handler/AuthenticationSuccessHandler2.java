package com.cody.roughcode.security.handler;

import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler2 extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication); // tokenInfo 만들어서

        redisTemplate.opsForValue()
                .set(tokenInfo.getUserId().toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
        response.addHeader("Set-Cookie", tokenInfo.generateAccessToken().toString());
        response.addHeader("Set-Cookie", tokenInfo.generateRefreshToken().toString());
    }
}

