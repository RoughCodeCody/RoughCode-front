package com.cody.roughcode.code.controller;

import com.cody.roughcode.code.dto.req.CodeReq;
import com.cody.roughcode.code.dto.res.CodeDetailRes;
import com.cody.roughcode.code.dto.res.CodeInfoRes;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                  @Parameter(description = "정렬 기준") @RequestParam(defaultValue = "modifiedDate") String sort,
                                  @Parameter(description = "페이지 수") @RequestParam(defaultValue = "0") int page,
                                  @Parameter(description = "한 페이지에 담기는 개수") @RequestParam(defaultValue = "10") int size,
                                  @Parameter(description = "검색어") @RequestParam(defaultValue = "") String keyword,
                                  @Parameter(description = "태그 아이디 리스트") @RequestParam(defaultValue = "") String tagIdList){
        Long userId = accessToken!= null? jwtTokenProvider.getId(accessToken): -1L;

        List<String> sortList = List.of("modifiedDate", "likeCnt", "feedbackCnt");
        log.info("요청 받음!!" + sort, page, size);
        if(!sortList.contains(sort) || page < 0 || size < 0){
            return Response.badRequest("잘못된 요청입니다");
        }

        List<CodeInfoRes> res = null;
        try{
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
            res = codesService.getCodeList(sort, pageRequest, keyword, tagIdList, userId);
        } catch(Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nextPage", page+1);
        resultMap.put("list", res);
        return Response.makeResponse(HttpStatus.OK, "코드 목록 조회 성공", res.size(), resultMap);
    }

    @Operation(summary = "코드 정보 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 정보 등록 성공"),
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "코드 정보 등록 실패")
    })
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "코드 상세 조회 성공", content = @Content(schema = @Schema(implementation = CodeDetailRes.class))),
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "코드 상세 조회 실패")
    })
    @GetMapping("/{codeId}")
    ResponseEntity<?> getCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true) @PathVariable Long codeId) {
        Long userId = accessToken!= null? jwtTokenProvider.getId(accessToken): -1L;

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
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "코드 정보 수정 실패")
    })
    @PutMapping("/{codeId}")
    ResponseEntity<?> updateCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true) @PathVariable Long codeId,
                                 @Parameter(description = "코드 정보 값", required = true) @RequestBody CodeReq codeReq) {
        Long userId = jwtTokenProvider.getId(accessToken);

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
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "코드 정보 삭제 실패")
    })
    @DeleteMapping("/{codeId}")
    ResponseEntity<?> updateCode(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                 @Parameter(description = "코드 id 값", required = true) @PathVariable Long codeId) {
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = codesService.deleteCode(codeId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if (res <= 0) {
            return Response.notFound("코드 정보 삭제 실패");
        }
        return Response.makeResponse(HttpStatus.OK, "코드 정보 삭제 성공", 1, res);

    }
}
