package com.cody.roughcode.mypage.controller;

import com.cody.roughcode.alarm.entity.Alarm;
import com.cody.roughcode.alarm.entity.AlarmRes;
import com.cody.roughcode.alarm.service.AlarmService;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AlarmService alarmService;

    @Operation(summary = "알림 조회 API")
    @GetMapping("/alarm")
    ResponseEntity<?> getAlarmList(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken){
        Long usersId = jwtTokenProvider.getId(accessToken);

        List<AlarmRes> res = new ArrayList<>();
        try {
            res = alarmService.getAlarmList(usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }
        return Response.makeResponse(HttpStatus.OK, "알림 조회 성공", res.size(), res);
    }

    @Operation(summary = "알림 삭제 API")
    @DeleteMapping("/alarm/{alarmId}")
    ResponseEntity<?> deleteAlarm(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                   @Parameter(description = "알람 아이디") @PathVariable String alarmId){
        Long usersId = jwtTokenProvider.getId(accessToken);

        try {
            alarmService.deleteAlarm(alarmId, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }
        return Response.ok("알림 삭제 성공");
    }
}
