package com.cody.roughcode.user.controller;

import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.security.auth.TokenInfo;
import com.cody.roughcode.user.dto.req.UserReq;
import com.cody.roughcode.user.dto.res.UserResp;
import com.cody.roughcode.user.service.JwtService;
import com.cody.roughcode.user.service.UsersService;
import com.cody.roughcode.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> selectOneUser(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        Long userId = jwtTokenProvider.getId(accessToken);

        UserResp resp = null;
        try{
            resp = usersService.selectOneUser(userId);
        } catch (NullPointerException e){
            return Response.notFound("회원 정보가 존재하지 않습니다.");
        } catch (Exception e){
            return Response.badRequest("잘못된 요청입니다.");
        }
        return Response.makeResponse(HttpStatus.OK, "회원 정보 조회 성공", 1, resp);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, @RequestBody UserReq userReq) {
        Long userId = jwtTokenProvider.getId(accessToken);

        try{
           usersService.updateUser(userId, userReq);
        } catch (NullPointerException e){
            return Response.notFound("회원 정보가 존재하지 않습니다.");
        } catch (Exception e){
            return Response.badRequest("잘못된 요청입니다.");
        }
        return Response.ok("회원 정보 수정 성공");

    }

    @GetMapping("/nicknameCheck")
    public ResponseEntity<?> checkNickname(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, String nickname) {
        Long userId = jwtTokenProvider.getId(accessToken);

        boolean duplicated = usersService.checkNickname(nickname);

        Map<String, Object> res = new HashMap<>();
        res.put("duplicated", duplicated);

        if (duplicated) {
            return Response.makeResponse(HttpStatus.OK, "중복된 닉네임입니다. 다른 닉네임을 입력해주세요.", 1, res);
        }
        return Response.makeResponse(HttpStatus.OK, "사용 가능한 닉네임입니다.", 0, res);
    }

    @PostMapping("/token")
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            TokenInfo tokenInfo = jwtService.reissue(request);
            response.addHeader("Set-Cookie", tokenInfo.generateAccessToken().toString());
            response.addHeader("Set-Cookie", tokenInfo.generateRefreshToken().toString());
        } catch (NullPointerException e){
            return Response.badRequest("회원 정보가 존재하지 않습니다.");
        } catch (Exception e){
            return Response.badRequest("잘못된 요청입니다.");
        }
        return Response.ok("Token 재발급 성공");
    }

}
