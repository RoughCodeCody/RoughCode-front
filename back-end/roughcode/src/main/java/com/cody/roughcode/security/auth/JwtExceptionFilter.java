package com.cody.roughcode.security.auth;

import com.cody.roughcode.exception.NoTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtExceptionFilter extends GenericFilter {


    public JwtExceptionFilter() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response); //jwtauthenticaionfilter
        } catch (NoTokenException e) {
            setErrorResponse(response, "토큰이 없습니다");
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, "토큰이 만료되었습니다");
        } catch (MalformedJwtException e) {
            setErrorResponse(response, "손상된 토큰입니다");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, "지원하지 않는 토큰입니다");
        } catch (SignatureException e) {
            setErrorResponse(response, "시그니처 검증에 실패한 토큰입니다");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, "토큰에 해당하는 유저가 없습니다");
        }
    }

    private void setErrorResponse(ServletResponse response, String error) throws IOException {
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding("UTF-8");
//        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, error);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        body.put("error", "Unauthorized");
        body.put("message", error);
        body.put("count", 0);
        body.put("result", null);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), body);
    }
}
