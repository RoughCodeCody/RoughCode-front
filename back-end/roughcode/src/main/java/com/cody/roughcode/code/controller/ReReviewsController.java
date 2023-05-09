package com.cody.roughcode.code.controller;


import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.service.ReReviewsServiceImpl;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/code/review/rereview")
@RequiredArgsConstructor
@Slf4j
public class ReReviewsController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReReviewsServiceImpl reReviewsService;

    @Operation(summary = "코드 리-리뷰 등록 API")
    @PostMapping
    ResponseEntity<?> insertReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                   @Parameter(description = "코드 리-리뷰 정보 값", required = true)
                                   @Valid @RequestBody ReReviewReq reReviewReq) {
        Long userId = (accessToken!= null)? jwtTokenProvider.getId(accessToken) : -1L;

        int res = 0;
        try {
            res = reReviewsService.insertReReview(reReviewReq, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 리-리뷰 등록 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리-리뷰 등록 성공", 1, res);
    }

}
