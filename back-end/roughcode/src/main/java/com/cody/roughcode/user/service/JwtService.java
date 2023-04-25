package com.cody.roughcode.user.service;

import com.cody.roughcode.security.auth.TokenInfo;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
    TokenInfo reissue(HttpServletRequest request);
}

