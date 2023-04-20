package com.cody.roughcode.config;

import com.cody.roughcode.security.auth.JwtAuthenticationFilter;
import com.cody.roughcode.security.auth.JwtExceptionFilter;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.handler.AuthenticationFailureHandler;
import com.cody.roughcode.security.handler.AuthenticationSuccessHandler;
import com.cody.roughcode.security.handler.CustomLogoutHandler;
import com.cody.roughcode.security.oauth2.CustomOAuth2AuthorizationRequestRepository;
import com.cody.roughcode.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    private final CustomOAuth2AuthorizationRequestRepository<OAuth2AuthorizationRequest> customOAuth2AuthorizationRequestRepository;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final CustomLogoutHandler customLogoutHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/user/token", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .httpBasic().disable() // 기본 로그인 화면 비활성화
                .formLogin().disable() // 폼로그인 비활성화
                .csrf().disable()   // csrf 보안 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용으로 session 비활성화
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL
//                .logoutSuccessUrl("/login") // 로그아웃 성공후 이동할 페이지
                .deleteCookies("accessToken", "refreshToken") // 쿠키 삭제
                .addLogoutHandler(customLogoutHandler)// 로그아웃 구현할 class 넣기
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()//authenticated() // 인가 검증
                .and()
                .oauth2Login()
                .authorizationEndpoint(authorize -> {
                    authorize.authorizationRequestRepository(
                            customOAuth2AuthorizationRequestRepository);
                })
                .userInfoEndpoint(userInfo -> {
                    userInfo.userService(customOAuth2UserService);
                })
                .loginProcessingUrl("/oauth/login/*") //auth/login/google/code=2358072305dfs
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .addFilter(corsConfig.corsFilter()) // cors 설정. 일단 전부 풀어놓음
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
