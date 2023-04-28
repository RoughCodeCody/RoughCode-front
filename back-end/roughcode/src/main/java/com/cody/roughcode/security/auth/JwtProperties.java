package com.cody.roughcode.security.auth;

//@Configuration

public interface JwtProperties {
    String TOKEN_HEADER = "Authorization";
    int ACCESS_TOKEN_TIME = 30 * 1000 * 60; // 30분
    int REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000; // 7일
    String AUTHORITIES_KEY = "auth";
    String REFRESH_TOKEN = "refreshToken";
    String ACCESS_TOKEN = "accessToken";
}

