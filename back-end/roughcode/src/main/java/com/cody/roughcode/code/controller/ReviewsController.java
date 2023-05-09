package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.ReviewReq;
import com.cody.roughcode.code.dto.res.ReviewDetailRes;
import com.cody.roughcode.code.service.ReviewsService;
import com.cody.roughcode.exception.SelectedException;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            @ApiResponse(responseCode = "400", description = "일치하는 코드가 존재하지 않습니다"),
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

    @Operation(summary = "코드 리뷰 상세 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 상세 조회 성공", content = @Content(schema = @Schema(implementation = ReviewDetailRes.class))),
            @ApiResponse(responseCode = "400", description = "일치하는 코드 리뷰가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 상세 조회 실패")
    })
    @GetMapping("/{reviewId}")
    ResponseEntity<?> getReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                              @Parameter(description = "코드 리뷰 id 값", required = true) @PathVariable Long reviewId) {
        Long userId = accessToken != null ? jwtTokenProvider.getId(accessToken) : -1L;

        ReviewDetailRes res = null;
        try {
            res = reviewsService.getReview(reviewId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res == null) {
            return Response.notFound("코드 리뷰 상세 조회 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 상세 조회 성공", 1, res);

    }

    @Operation(summary = "코드 리뷰 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드 리뷰가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 수정 실패"),
            @ApiResponse(responseCode = "409", description = "채택된 코드 리뷰는 수정할 수 없습니다"),
    })
    @PutMapping("/{reviewId}")
    ResponseEntity<?> updateReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                   @Parameter(description = "코드 리뷰 id 값", required = true) @PathVariable Long reviewId,
                                   @Parameter(description = "코드 리뷰 정보 값", required = true) @RequestBody ReviewReq reviewReq) {
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = reviewsService.updateReview(reviewReq, reviewId, userId);
        } catch(SelectedException e){
            return Response.conflict(e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 리뷰 수정 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 수정 성공", 1, res);
    }

    @Operation(summary = "코드 리뷰 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드 리뷰가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 삭제 실패"),
            @ApiResponse(responseCode = "409", description = "채택된 코드 리뷰는 삭제할 수 없습니다"),
    })
    @DeleteMapping("/{reviewId}")
    ResponseEntity<?> deleteReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                   @Parameter(description = "코드 리뷰 id 값", required = true) @PathVariable Long reviewId) {
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = reviewsService.deleteReview(reviewId, userId);
        } catch(SelectedException e){
            return Response.conflict(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 리뷰 삭제 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 삭제 성공", 1, res);
    }

    @Operation(summary = "코드 리뷰 좋아요(등록, 취소) API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 좋아요 등록 또는 취소 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드 리뷰가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 좋아요 등록 또는 취소 실패")
    })
    @PostMapping("/{reviewId}/like")
    ResponseEntity<?> likeReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                               @Parameter(description = "코드 리뷰 id 값", required = true) @PathVariable Long reviewId) {
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = reviewsService.likeReview(reviewId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res < 0) {
            return Response.notFound("코드 리뷰 좋아요 등록 또는 취소 실패");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("like", res);
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 좋아요 등록 또는 취소 성공", 0, resultMap);

    }

    @Operation(summary = "코드 리뷰 신고 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 신고 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드 리뷰가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 신고 실패"),
            @ApiResponse(responseCode = "409", description = "이미 삭제된 or 신고한 코드 리뷰입니다"),
    })
    @PutMapping("/{reviewId}/complaint")
    ResponseEntity<?> complainReview(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                     @Parameter(description = "코드 리뷰 id 값", required = true) @PathVariable Long reviewId){
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = reviewsService.complainReview(reviewId, userId);
        } catch(SelectedException e){
            return Response.conflict(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) {
            return Response.notFound("코드 리뷰 신고 실패");
        }
        return Response.ok("코드 리뷰 신고 성공");
    }
}
