package com.cody.roughcode.security.auth;

import com.cody.roughcode.security.dto.TokenReq;
import com.cody.roughcode.user.dto.res.UserRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // Cookie 에서 JWT Token 추출
//        String token = jwtTokenProvider.getAccessToken(request);
//        if (!((HttpServletRequest) request).getRequestURI().equals("/api/v1/user/token")) {
//            //재발급 요청이 아니라면
//            if (token != null && jwtTokenProvider.validateToken(token)) {
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                log.debug(authentication.getName() + "의 인증정보 저장");
//            } else {
//                log.debug("유효한 JWT 토큰이 없습니다");
//            }
//        }

        // Cookie 에서 JWT Token 추출
        TokenReq tokenReq = jwtTokenProvider.getToken((HttpServletRequest) request);
        String accessToken = tokenReq.getAccessToken();
        String refreshToken = tokenReq.getRefreshToken();

        if (accessToken != null) {
            // accessToken 값이 유효하다면 getAuthentication 통해 security context에 인증 정보 저장
            if (jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug(authentication.getName() + "의 인증정보 저장");
            }
            // accessToken 값 만료됨 && refreshToken 존재
            else if (refreshToken != null) {
                log.info("refreshToken 유효함 && Redis에 저장된 토큰과 비교해서 똑같다면");

                // 받은 토큰에서 유저 정보 가져오기
                Long userId = jwtTokenProvider.getId(accessToken);

                // refreshToken 검증
                boolean isRefreshToken = validateRefreshToken(refreshToken, userId);

                // refreshToken 유효함 && Redis에 저장된 토큰과 비교해서 똑같다면
                if (isRefreshToken) {
                    log.info("refreshToken 유효함 && Redis에 저장된 토큰과 비교해서 똑같다면");
                    //새로운 토큰 생성
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

                    //redis refreshToken 업데이트
                    redisTemplate.opsForValue()
                            .set(userId.toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);

                    // Security content에 인증 정보 넣기
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                // refreshToken 만료 || Redis에 저장된 토큰과 비교해서 똑같지 않다면
                else {
                    log.info("refreshToken 만료 || Redis에 저장된 토큰과 비교해서 똑같지 않다면");
                    UserRes userRes = UserRes.builder()
                            .nickname("")
                            .email("")
                            .projectsCnt(-1L)
                            .codesCnt(-1L)
                            .build();
                    if (((HttpServletRequest) request).getRequestURI().equals("/api/v1/user")) {
                        log.info("회원정보 조회 성공!! 로그아웃 됨");
                        jwtExceptionHandler((HttpServletResponse) response, "회원 정보 조회 성공(로그아웃된 상태)", userRes, HttpServletResponse.SC_OK );
                    } else {
                        jwtExceptionHandler((HttpServletResponse) response, "유효한 JWT 토큰 없음(로그아웃된 상태)", userRes, HttpServletResponse.SC_OK);

                    }
                }
            }
        } else { // accessToken 없음
            log.info("accessToken 없음");
            UserRes userRes = UserRes.builder()
                    .nickname("")
                    .email("")
                    .projectsCnt(-1L)
                    .codesCnt(-1L)
                    .build();
            if (((HttpServletRequest) request).getRequestURI().equals("/api/v1/user")) {
                log.info("회원정보 조회 성공!! 로그아웃 됨");
                jwtExceptionHandler((HttpServletResponse) response, "회원 정보 조회 성공(로그아웃된 상태)", userRes, HttpServletResponse.SC_OK );
            } else {
                jwtExceptionHandler((HttpServletResponse) response, "유효한 JWT 토큰 없음(로그아웃된 상태)", userRes, HttpServletResponse.SC_OK);

            }
        }

//        chain.doFilter(request, response);
    }

    // refresh 토큰 정보를 검증하는 메서드
    public boolean validateRefreshToken(String token, Long userId) {

        // 1차 토큰 검증
        if(!jwtTokenProvider.validateToken(token)) {
            return false;
        }

        // Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        String saveRefreshToken = (String) redisTemplate.opsForValue().get(userId.toString());

        // Redis에 저장한 토큰 비교
        return saveRefreshToken!= null && saveRefreshToken.equals(token);

    }

    // Jwt 예외처리
    public void jwtExceptionHandler(HttpServletResponse response, String msg, Object result, int status) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        final Map<String, Object> body = new HashMap<>();
        body.put("message", msg);
        body.put("count", 0);
        body.put("result", result);

        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getOutputStream(), body);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}


