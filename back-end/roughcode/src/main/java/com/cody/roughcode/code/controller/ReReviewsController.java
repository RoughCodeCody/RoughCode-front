package com.cody.roughcode.code.controller;


import com.cody.roughcode.code.dto.req.ReReviewReq;
import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReReviewRes;
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
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/code/rereview")
@RequiredArgsConstructor
@Slf4j
public class ReReviewsController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReReviewsServiceImpl reReviewsService;

    @Operation(summary = "코드 리-리뷰 삭제 API")
    @DeleteMapping("/{reReviewId}")
    ResponseEntity<?> deleteReReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = true) String accessToken,
                                   @Parameter(description = "삭제할 리-리뷰 아이디", required = true)
                                   @Min(value = 1, message = "reReviewId 값이 범위를 벗어납니다")
                                   @PathVariable Long reReviewId) {
        Long userId = (accessToken!= null)? jwtTokenProvider.getId(accessToken) : -1L;

        int res = 0;
        try {
            res = reReviewsService.deleteReReview(reReviewId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("코드 리-리뷰 삭제 실패");
        return Response.ok("코드 리-리뷰 삭제 성공");
    }

    @Operation(summary = "코드 리-리뷰 목록 조회 API")
    @GetMapping("/{reviewId}")
    ResponseEntity<?> getReReviewList(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                   @Parameter(description = "코드 리뷰 아이디", required = true)
                                   @Min(value = 1, message = "reviewId 값이 범위를 벗어납니다")
                                   @PathVariable Long reviewId) {
        Long userId = (accessToken!= null)? jwtTokenProvider.getId(accessToken) : -1L;

        List<ReReviewRes> res = new ArrayList<>();
        try {
            res = reReviewsService.getReReviewList(reviewId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        return Response.makeResponse(HttpStatus.OK, "코드 리-리뷰 조회 성공", res.size(), res);
    }

    @Operation(summary = "코드 리-리뷰 수정 API")
    @PutMapping
    ResponseEntity<?> updateReReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = true) String accessToken,
                                   @Parameter(description = "코드 리-리뷰 정보 값", required = true)
                                   @Valid @RequestBody ReReviewReq reReviewReq) {
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = reReviewsService.updateReReview(reReviewReq, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 리-리뷰 수정 실패");
        }
        return Response.ok("코드 리-리뷰 수정 성공");
    }

    @Operation(summary = "코드 리-리뷰 등록 API")
    @PostMapping
    ResponseEntity<?> insertReReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
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
