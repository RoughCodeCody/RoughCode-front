package com.cody.roughcode.user.service;

import com.cody.roughcode.exception.InvalidTokenException;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.auth.TokenInfo;
import com.cody.roughcode.security.dto.TokenReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override
    public TokenInfo reissue(HttpServletRequest request) {
        TokenReq tokenReq = jwtTokenProvider.getToken(request);
        String accessToken = tokenReq.getAccessToken();
        String refreshToken = tokenReq.getRefreshToken();

        //RefreshToken 검증
        try {
            jwtTokenProvider.validateToken(refreshToken);
        } catch (Exception e) {
//            return null;
            throw new InvalidTokenException();
        }

        //받은 토큰에서 유저 정보 가져오기
        Long userId = jwtTokenProvider.getId(accessToken);
        //Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        String saveRefreshToken = (String) redisTemplate.opsForValue().get(userId.toString());

        // 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우
        if (saveRefreshToken == null) {
            return null;
//            return new ResponseEntity<>("로그아웃이 된 사람", HttpStatus.BAD_REQUEST);
        }

        //보낸 리프레쉬 토큰과 찾은 리프레쉬 토큰이 다른 경우
        if (!saveRefreshToken.equals(refreshToken)) {
            return null;
//            return new ResponseEntity<>("Refresh 정보가 일치하지 않음", HttpStatus.BAD_REQUEST);
        }

        //새로운 토큰 생성
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //redis refreshToken 업데이트
        redisTemplate.opsForValue()
                .set(userId.toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);

        return tokenInfo;
    }


}
