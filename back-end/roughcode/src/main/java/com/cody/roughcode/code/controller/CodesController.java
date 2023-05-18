package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeFavoriteReq;
import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.*;
import com.cody.roughcode.code.service.CodesService;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
@Slf4j
public class CodesController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CodesService codesService;

    @Operation(summary = "코드 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 목록 조회 성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CodeInfoRes.class)))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "404", description = "코드 목록 조회 실패")
    })
    @GetMapping()
    ResponseEntity<?> getCodeList(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                  @Parameter(description = "정렬 기준 ('createdDate':최신순, 'likeCnt':좋아요수 많은순, 'reviewCnt':리뷰수 많은순)", example = "createdDate") @RequestParam(defaultValue = "createdDate") String sort,
                                  @Parameter(description = "페이지 수", example = "0") @RequestParam(defaultValue = "0") int page,
                                  @Parameter(description = "한 페이지에 담기는 개수", example = "10") @RequestParam(defaultValue = "10") int size,
                                  @Parameter(description = "검색어", example = "개발새발") @RequestParam(defaultValue = "") String keyword,
                                  @Parameter(description = "태그 아이디 리스트", example = "1,2,3,4") @RequestParam(defaultValue = "") String tagIdList) {
        Long userId = accessToken != null ? jwtTokenProvider.getId(accessToken) : -1L;

        List<String> sortList = List.of("createdDate", "likeCnt", "reviewCnt");

        if (!sortList.contains(sort) || page < 0 || size < 0) {
            return Response.badRequest("잘못된 요청입니다");
        }

        List<CodeInfoRes> res = null;
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
            res = codesService.getCodeList(sort, pageRequest, keyword, tagIdList, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nextPage", page + 1);
        resultMap.put("list", res);
        return Response.makeResponse(HttpStatus.OK, "코드 목록 조회 성공", res.size(), resultMap);
    }

    @Operation(summary = "코드 정보 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 정보 등록 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 정보 등록 실패")
    })
    @PostMapping()
    ResponseEntity<?> insertCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 정보 값", required = true) @Valid @RequestBody CodeReq codeReq) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        if (codeReq.getCodeId() == 0 || codeReq.getCodeId() < -1) {
            return Response.badRequest("codeId 값이 범위를 벗어납니다");
        }

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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 상세 조회 성공", content = @Content(schema = @Schema(implementation = CodeDetailRes.class))),
            @ApiResponse(responseCode = "400", description = "일치하는 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 상세 조회 실패")
    })
    @GetMapping("/{codeId}")
    ResponseEntity<?> getCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                              @Parameter(description = "코드 id 값", required = true)
                              @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                              @PathVariable Long codeId) {
        Long userId = accessToken != null ? jwtTokenProvider.getId(accessToken) : -1L;

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

    @Operation(summary = "코드 정보 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드 or 연결된 프로젝트가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 정보 수정 실패")
    })
    @PutMapping("/{codeId}")
    ResponseEntity<?> updateCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true)
                                 @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                 @PathVariable Long codeId,
                                 @Parameter(description = "코드 정보 값", required = true) @Valid @RequestBody CodeReq codeReq) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        int res = 0;
        try {
            res = codesService.updateCode(codeReq, codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 정보 수정 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 정보 수정 성공", 1, res);

    }

    @Operation(summary = "코드 정보 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 정보 삭제 실패")
    })
    @DeleteMapping("/{codeId}")
    ResponseEntity<?> updateCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true)
                                 @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                 @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        int res = 0;
        try {
            res = codesService.putExpireDateCode(codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 정보 삭제 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 정보 삭제 성공", 1, res);

    }

    @Operation(summary = "코드 좋아요(등록, 취소) API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 좋아요 등록 또는 취소 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 좋아요 등록 또는 취소 실패")
    })
    @PostMapping("/{codeId}/like")
    ResponseEntity<?> likeCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                               @Parameter(description = "코드 id 값", required = true)
                               @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                               @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        int res = 0;
        try {
            res = codesService.likeCode(codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res < 0) {
            return Response.notFound("코드 좋아요 등록 또는 취소 실패");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("like", res);
        return Response.makeResponse(HttpStatus.OK, "코드 좋아요 등록 또는 취소 성공", 0, resultMap);

    }

    @Operation(summary = "코드 즐겨찾기(등록, 취소) API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 즐겨찾기 등록 또는 취소 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 즐겨찾기 등록 또는 취소 실패")
    })
    @PostMapping("/{codeId}/favorite")
    ResponseEntity<?> favoriteCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                   @Parameter(description = "즐겨찾기 내용") @RequestBody(required = false) CodeFavoriteReq codeFavoriteReq,
                                   @Parameter(description = "코드 id 값", required = true)
                                   @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                   @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        String content = codeFavoriteReq != null ? codeFavoriteReq.getContent() : null;

        int res = -1;
        try {
            res = codesService.favoriteCode(codeId, content, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res < 0) {
            return Response.notFound("코드 즐겨찾기 등록 또는 취소 실패");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("favorite", res);
        return Response.makeResponse(HttpStatus.OK, "코드 즐겨찾기 등록 또는 취소 성공", 0, resultMap);

    }

    @Operation(summary = "코드 태그 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 태그 목록 조회 성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CodeTagsRes.class)))
            }),
            @ApiResponse(responseCode = "400", description = "코드 태그 목록 조회 실패")
    })
    @GetMapping("/tag")
    ResponseEntity<?> searchTags(@Parameter(description = "검색 키워드") @RequestParam String keyword) {
        List<CodeTagsRes> res;
        try {
            res = codesService.searchTags(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest("코드 태그 목록 조회 실패");
        }

        return Response.makeResponse(HttpStatus.OK, "코드 태그 목록 조회 성공", res.size(), res);
    }

    @Operation(summary = "코드 리뷰 목록 조회 API - 코드 등록/수정 시 사용")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 목록 조회 성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReviewInfoRes.class)))
            }),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 목록 조회 실패")
    })
    @GetMapping("/{codeId}/review")
    ResponseEntity<?> getReviewList(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "코드 id 값", required = true)
                                    @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                    @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        List<ReviewInfoRes> res = null;
        try {
            res = codesService.getReviewList(codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res == null) {
            return Response.notFound("코드 리뷰 목록 조회 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 목록 조회 성공", res.size(), res);
    }

    @Operation(summary = "코드 리뷰 목록 조회 API - 코드 상세조회 시 사용")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 리뷰 목록 조회 성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReviewSearchRes.class)))
            }),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 리뷰 목록 조회 실패")
    })
    @GetMapping("/{codeId}/code-review")
    ResponseEntity<?> getCodeReviewList(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                        @Parameter(description = "코드 id 값", required = true)
                                        @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                        @PathVariable Long codeId,
                                        @Parameter(description = "검색어") @RequestParam(defaultValue = "") String keyword) {
        Long userId = accessToken != null ? jwtTokenProvider.getId(accessToken) : -1L;

        List<ReviewSearchRes> res = null;
        try {
            res = codesService.getReviewSearchList(codeId, userId, keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res == null) {
            return Response.notFound("코드 리뷰 목록 조회 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 리뷰 목록 조회 성공", res.size(), res);
    }

    @Operation(summary = "코드와 프로젝트 연결 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 프로젝트 연결 성공"),
            @ApiResponse(responseCode = "400", description = "일치하는 유저 or 코드가 존재하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "코드 프로젝트 연결 실패")
    })
    @PutMapping("/{codeId}/connect/{projectId}")
    ResponseEntity<?> connectCodeProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                         @Parameter(description = "코드 id 값", required = true)
                                         @Range(min = 1, max = Long.MAX_VALUE, message = "codeId 값이 범위를 벗어납니다")
                                         @PathVariable Long codeId,
                                         @Parameter(description = "연결하려는 프로젝트 id 값")
                                         @Range(min = -1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                         @PathVariable Long projectId) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if (userId <= 0) {
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");
        }

        int res = -1;
        try {
            res = codesService.connectProject(codeId, userId, projectId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res < 0) {
            return Response.notFound("코드와 프로젝트 연결 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드와 프로젝트 연결 성공", 0, res);
    }

    @Operation(summary = "코드 언어 목록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 언어 목록 조회 성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CodeTagsRes.class)))
            }),
            @ApiResponse(responseCode = "400", description = "코드 언어 목록 조회 실패")
    })
    @GetMapping("/language")
    ResponseEntity<?> searchLanguages(@Parameter(description = "검색 키워드") @RequestParam String keyword) {
        List<CodeLanguagesRes> res;
        try {
            res = codesService.searchLanguages(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest("코드 언어 목록 조회 실패");
        }

        return Response.makeResponse(HttpStatus.OK, "코드 언어 목록 조회 성공", res.size(), res);
    }
}
