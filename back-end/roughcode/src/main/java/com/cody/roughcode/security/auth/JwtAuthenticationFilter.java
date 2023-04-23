package com.cody.roughcode.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cookie 에서 JWT Token 추출
        String token = jwtTokenProvider.getAccessToken(request);
        if (!((HttpServletRequest) request).getRequestURI().equals("/tokens/reissue")) {
            //재발급 요청이 아니라면
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug(authentication.getName() + "의 인증정보 저장");
            } else {
                log.debug("유효한 JWT 토큰이 없습니다");
            }
        }

        chain.doFilter(request, response);
    }

}


