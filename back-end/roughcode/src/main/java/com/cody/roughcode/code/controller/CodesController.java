package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.CodeDetailRes;
import com.cody.roughcode.code.service.CodesService;
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

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
@Slf4j
public class CodesController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CodesService codesService;

    @Operation(summary = "코드 정보 등록 API")
    @PostMapping()
    ResponseEntity<?> insertCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 정보 값", required = true) @RequestBody CodeReq codeReq) {
        Long userId = jwtTokenProvider.getId(accessToken);

        Long res = 0L;
        try {
            res = codesService.insertCode(codeReq, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 정보 등록 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 정보 등록 성공", 1, res);

    }

    @Operation(summary = "코드 상세 조회 API")
    @GetMapping("/{codeId}")
    ResponseEntity<?> getCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true) @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);

        CodeDetailRes res = null;
        try {
            res = codesService.getCode(codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res == null) {
            return Response.notFound("코드 상세 조회 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 상세 조회 성공", 1, res);

    }
}
