package com.cody.roughcode.security.handler;

import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.auth.TokenInfo;
import com.cody.roughcode.security.oauth2.CookieOAuth2AuthorizationRequestRepository;
import com.cody.roughcode.exception.BadRequestException;
import com.cody.roughcode.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cody.roughcode.security.oauth2.CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.authorizedRedirectUri}")
    private List<String> redirectUris;
    @Value("${app.host}")
    private String host;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        // JWT 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication); // tokenInfo 생성

        // redis 저장
        redisTemplate.opsForValue()
                .set(tokenInfo.getUserId().toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
        // access token, refresh token 쿠키에 저장
        response.addHeader("Set-Cookie", tokenInfo.generateAccessToken().toString());
        response.addHeader("Set-Cookie", tokenInfo.generateRefreshToken().toString());

        if(response.isCommitted()){
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        log.info("쿠키 발급 완료. 리다이렉트 : " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        // 리다이렉트 URI 일치하는지 체크하는 부분 (테스트중일 때는 주석처리함)
//        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
////        if (redirectUri.isPresent()) {
//            throw new BadRequestException("redirect URIs are not matched");
//        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        return UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        Set<URI> authorizedUris = new HashSet<>();
        for(String redirectUri: redirectUris){
            authorizedUris.add(URI.create(redirectUri));
        }
        Set<String> authorizedHosts = new HashSet<>();
        Set<Integer> authorizedPorts = new HashSet<>();
        for (URI authorizedUri : authorizedUris) {
            String host = authorizedUri.getHost();
            if (host != null) { // 호스트가 null이 아니면 리스트에 추가
                authorizedHosts.add(host);
            }

            Integer port = authorizedUri.getPort();
            if (port != null) { // 포트번호가 null이 아니면 리스트에 추가
                authorizedPorts.add(port);
            }
        }

        boolean hostCheck = authorizedHosts.stream()
                .map(String::toLowerCase) // 모든 문자열 소문자로 변환
                .anyMatch(s -> s.contains(clientRedirectUri.getHost().toLowerCase())); // 대소문자 구분없이 포함여부 확인

        boolean portCheck = clientRedirectUri.getPort() == -1 || authorizedPorts.contains(clientRedirectUri.getPort());

        if (hostCheck && portCheck) {
            return true;
        }
        return false;
    }
}
