package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.FeedbackReq;
import com.cody.roughcode.project.dto.req.FeedbackUpdateReq;
import com.cody.roughcode.project.dto.res.FeedbackInfoRes;
import com.cody.roughcode.project.dto.res.ProjectDetailRes;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.dto.res.ProjectTagsRes;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectsController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectsServiceImpl projectsService;

    @Operation(summary = "프로젝트 즐겨찾기 등록 또는 취소 API")
    @PostMapping("/{projectId}/favorite")
    ResponseEntity<?> favoriteProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                      @Parameter(description = "즐겨찾기 내용") @RequestBody String content,
                                      @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);

        int res = -1;
        try {
            res = projectsService.favoriteProject(projectId, content, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res < 0) return Response.notFound("프로젝트 즐겨찾기 등록 또는 취소 실패");
        return Response.makeResponse(HttpStatus.OK, "프로젝트 즐겨찾기 등록 또는 취소 성공", 1, res);
    }

    @Operation(summary = "프로젝트 좋아요 또는 취소 API")
    @PostMapping("/{projectId}/like")
    ResponseEntity<?> likeProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                  @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);

        int res = -1;
        try {
            res = projectsService.likeProject(projectId, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res < 0) return Response.notFound("프로젝트 좋아요 또는 취소 실패");
        return Response.makeResponse(HttpStatus.OK, "프로젝트 좋아요 또는 취소 성공", 1, res);
    }

    @Operation(summary = "프로젝트 태그 목록 조회 API")
    @GetMapping("/tag")
    ResponseEntity<?> searchTags(@Parameter(description = "검색 키워드") @RequestParam String keyword) {
        List<ProjectTagsRes> res;
        try {
            res = projectsService.searchTags(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest("프로젝트 태그 목록 조회 실패");
        }

        return Response.makeResponse(HttpStatus.OK, "프로젝트 태그 목록 조회 성공", res.size(),  res);
    }

    @Operation(summary = "프로젝트 열림 확인 API")
    @PutMapping("/check/{projectId}")
    ResponseEntity<?> isProjectOpen(@Parameter(description = "프로젝트 아이디") @PathVariable Long projectId){
        int res = -2;
        try{
            res = projectsService.isProjectOpen(projectId);
        } catch(Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= -2) return Response.notFound("프로젝트 열림 확인 실패");
        return Response.makeResponse(HttpStatus.OK, "프로젝트 열림 확인 성공", 1, res);
    }

    @Operation(summary = "프로젝트 URL 검사 API")
    @GetMapping("/check")
    ResponseEntity<?> projectURLCheck(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                      @Parameter(description = "프로젝트 url") @RequestParam String url){
        Long userId = jwtTokenProvider.getId(accessToken);

        Boolean res = false;
        try{
            res = projectsService.checkProject(url, userId);
        } catch(Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        return Response.makeResponse(HttpStatus.OK, "프로젝트 검사 성공", 1, res);
    }

    @Operation(summary = "피드백 삭제 API")
    @DeleteMapping("/feedback/{feedbackId}")
    ResponseEntity<?> deleteFeedback(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                      @Parameter(description = "피드백 아이디") @PathVariable Long feedbackId){
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = projectsService.deleteFeedback(feedbackId, userId);
        } catch(ResponseStatusException e){
            log.warn(e.getReason());
            return Response.makeResponse(e.getStatus(), e.getReason());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("피드백 삭제 실패");
        return Response.ok("피드백 삭제 성공");
    }

    @Operation(summary = "피드백 목록 조회 API")
    @GetMapping("/{projectId}/feedback")
    ResponseEntity<?> getFeedbackList(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                     @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId){
        Long userId = jwtTokenProvider.getId(accessToken);

        List<FeedbackInfoRes> res = null;
        try {
            res = projectsService.getFeedbackList(projectId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res == null) return Response.notFound("피드백 목록 조회 실패");
        return Response.makeResponse(HttpStatus.OK, "피드백 목록 조회 성공", res.size(), res);
    }

    @Operation(summary = "피드백 수정 API")
    @PutMapping("/feedback")
    ResponseEntity<?> updateFeedback(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                     @Parameter(description = "피드백 정보") @RequestBody FeedbackUpdateReq req){
        Long userId = jwtTokenProvider.getId(accessToken);

        Boolean res = false;
        try {
            res = projectsService.updateFeedback(req, userId);
        } catch(ResponseStatusException e){
            log.warn(e.getReason());
            return Response.makeResponse(e.getStatus(), e.getReason());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(!res) return Response.notFound("피드백 수정 실패");
        return Response.ok("피드백 수정 성공");
    }

    @Operation(summary = "피드백 등록 API")
    @PostMapping("/feedback")
    ResponseEntity<?> insertFeedback(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                     @Parameter(description = "피드백 정보") @RequestBody FeedbackReq req){
        Long userId = (accessToken != null)? jwtTokenProvider.getId(accessToken) : 0L;

        int res = 0;
        try {
            res = projectsService.insertFeedback(req, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("피드백 등록 실패");
        return Response.ok("피드백 등록 성공");
    }

    @Operation(summary = "프로젝트 상세 조회 API")
    @GetMapping("/{projectId}")
    ResponseEntity<?> getProjectList(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                     @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId) {
        Long userId = (accessToken != null)? jwtTokenProvider.getId(accessToken) : 0L;

        ProjectDetailRes res = null;
        try{
            res = projectsService.getProject(projectId, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res == null) return Response.notFound("프로젝트 상세 조회 실패");
        return Response.makeResponse(HttpStatus.OK, "프로젝트 상세 조회 성공", 1, res);
    }

    @Operation(summary = "피드백 신고 API")
    @PutMapping("/feedback/{feedbackId}/complaint")
    ResponseEntity<?> feedbackComplain(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                       @Parameter(description = "피드백 아이디") @PathVariable Long feedbackId){
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = projectsService.feedbackComplain(feedbackId, userId);
        } catch(ResponseStatusException e){
            log.warn(e.getReason());
            return Response.makeResponse(e.getStatus(), e.getReason());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 피드백 신고 실패");
        return Response.ok("프로젝트 피드백 신고 성공");
    }

    @Operation(summary = "프로젝트 목록 조회 API")
    @GetMapping
    ResponseEntity<?> getProjectList(@Parameter(description = "정렬 기준") @RequestParam(defaultValue = "modifiedDate") String sort,
                                    @Parameter(description = "페이지 수") @RequestParam(defaultValue = "0") int page,
                                     @Parameter(description = "한 페이지에 담기는 개수") @RequestParam(defaultValue = "10") int size,
                                     @Parameter(description = "검색어") @RequestParam(defaultValue = "") String keyword,
                                     @Parameter(description = "태그 아이디 리스트") @RequestParam(defaultValue = "") String tagIdList,
                                     @Parameter(description = "닫힘 포함 여부(0: 닫힘 미포함, 1: 닫힘 포함)") @RequestParam(defaultValue = "0") int closed) {
        List<String> sortList = List.of("modifiedDate", "likeCnt", "feedbackCnt");
        if(!sortList.contains(sort) || page < 0 || size < 0){
            return Response.badRequest("잘못된 요청입니다");
        }

        List<ProjectInfoRes> res = new ArrayList<>();
        try{
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
            res = projectsService.getProjectList(sort, pageRequest, keyword, tagIdList, closed);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nextPage", page + 1);
        resultMap.put("list", res);
        return Response.makeResponse(HttpStatus.OK, "프로젝트 목록 조회 성공", res.size(), resultMap);
    }

    @Operation(summary = "프로젝트 삭제 API")
    @DeleteMapping("/{projectId}")
    ResponseEntity<?> deleteProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId){
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try{
            res = projectsService.deleteProject(projectId, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 삭제 실패");
        else return Response.ok("프로젝트 삭제 성공");
    }

    @Operation(summary = "프로젝트 코드 연결 API")
    @PutMapping("/{projectId}/connect")
    ResponseEntity<?> connectProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                     @Parameter(description = "프로젝트 아이디") @PathVariable Long projectId,
                                     @Parameter(description = "연결할 코드 아이디 리스트", required = true) @RequestBody List<Long> req){
        Long userId = jwtTokenProvider.getId(accessToken);

        int res = 0;
        try {
            res = projectsService.connect(projectId, userId, req);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 코드 연결 실패");
        else return Response.makeResponse(HttpStatus.OK, "프로젝트 코드 연결 성공", 1, res);
    }

    @Operation(summary = "프로젝트 수정 API")
    @PutMapping("/content")
    ResponseEntity<?> updateProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "프로젝트 정보 값", required = true) @RequestBody ProjectReq req) {
        Long userId = jwtTokenProvider.getId(accessToken);
//        Long userId = 1L;

        int res = 0;
        try{
            res = projectsService.updateProject(req, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res == 0) return Response.notFound("프로젝트 정보 수정 실패");
        return Response.ok("프로젝트 정보 수정 성공");
    }

    @Operation(summary = "프로젝트 썸네일 등록/수정 API")
    @PostMapping(value = "/thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> updateProjectThumbnail(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "등록할 project id", required = true) @RequestParam("projectId") Long projectId,
                                    @Parameter(description = "등록할 썸네일", required = true) @RequestPart("thumbnail") MultipartFile thumbnail) {
        Long userId = jwtTokenProvider.getId(accessToken);
//        Long userId = 1L;

        int res = 0;
        try{
            res = projectsService.updateProjectThumbnail(thumbnail, projectId, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res == 0) return Response.notFound("프로젝트 썸네일 등록 실패");
        return Response.ok("프로젝트 썸네일 등록 성공");
    }

    @Operation(summary = "프로젝트 정보 등록 API")
    @PostMapping("/content")
    ResponseEntity<?> insertProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                     @Parameter(description = "프로젝트 정보 값", required = true) @RequestBody ProjectReq req) {
        Long userId = jwtTokenProvider.getId(accessToken);
//        Long userId = 2L;

        Long res = 0L;
        try{
            res = projectsService.insertProject(req, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 정보 등록 실패");
        return Response.makeResponse(HttpStatus.OK, "프로젝트 정보 등록 성공", 1, res);
    }
}