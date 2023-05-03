package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.service.ReviewsService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/code/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewsController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewsService reviewsService;

    @Operation(summary = "코드 리뷰 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 등록 성공"),
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 등록 실패")
    })
    @PostMapping()
    ResponseEntity<?> insertReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                 @Parameter(description = "코드 리뷰 정보 값", required = true) @RequestBody ReviewReq reviewReq) {
        Long userId = (accessToken!= null)? jwtTokenProvider.getId(accessToken) : -1L;
        log.info(reviewReq.getSelectedRange().toString());

        Long res = 0L;
        try {
            res = reviewsService.insertReview(reviewReq, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 리뷰 등록 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 등록 성공", 1, res);
    }
}
