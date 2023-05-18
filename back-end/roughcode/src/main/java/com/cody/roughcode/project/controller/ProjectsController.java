package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.FeedbackInsertReq;
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
import com.cody.roughcode.validation.EachPositive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectsController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectsServiceImpl projectsService;
    private final RedisTemplate<String, String> redisTemplate;

    @Operation(summary = "프로젝트 열기 API")
    @PutMapping("/{projectId}/open")
    ResponseEntity<?> openProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                            @Parameter(description = "프로젝트 아이디")
                            @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                            @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);
        if(usersId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = 0;
        try {
            res = projectsService.openProject(projectId, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 열기 실패");
        return Response.ok("프로젝트 열기 성공");
    }

    @Operation(summary = "프로젝트 닫기 API")
    @PutMapping("/{projectId}/close")
    ResponseEntity<?> closeProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                  @Parameter(description = "프로젝트 아이디")
                                  @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                  @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);
        if(usersId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = 0;
        try {
            res = projectsService.closeProject(projectId, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("프로젝트 닫기 실패");
        return Response.ok("프로젝트 닫기 성공");
    }

    @Operation(summary = "피드백 좋아요 또는 취소 API")
    @PostMapping("/feedback/{feedbackId}/like")
    ResponseEntity<?> likeProjectFeedback(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                          @Parameter(description = "피드백 아이디")
                                          @Range(min = 1, max = Long.MAX_VALUE, message = "feedbackId 값이 범위를 벗어납니다")
                                          @PathVariable Long feedbackId){
        Long usersId = jwtTokenProvider.getId(accessToken);
        if(usersId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = -1;
        try {
            res = projectsService.likeProjectFeedback(feedbackId, usersId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res < 0) return Response.notFound("피드백 좋아요 또는 취소 실패");
        return Response.makeResponse(HttpStatus.OK, "피드백 좋아요 또는 취소 성공", 1, res);
    }
    
    @Operation(summary = "프로젝트 즐겨찾기 등록 또는 취소 API")
    @PostMapping("/{projectId}/favorite")
    ResponseEntity<?> favoriteProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                      @Parameter(description = "프로젝트 아이디")
                                      @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                      @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);
        if(usersId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = -1;
        try {
            res = projectsService.favoriteProject(projectId, usersId);
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
                                  @Parameter(description = "프로젝트 아이디")
                                  @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                  @PathVariable Long projectId){
        Long usersId = jwtTokenProvider.getId(accessToken);
        if(usersId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

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
    ResponseEntity<?> isProjectOpen(@Parameter(description = "프로젝트 아이디")
                                    @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                    @PathVariable Long projectId){
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
                                      @Parameter(description = "프로젝트 url")
                                      @Pattern(regexp = "^https?://.*", message = "url 값은 http 또는 https로 시작해야 합니다")
                                      @RequestParam String url){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        Boolean res = false;
        try{
            res = projectsService.checkProject(url, false);
        } catch(Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        return Response.makeResponse(HttpStatus.OK, "프로젝트 검사 성공", 1, res);
    }

    @Operation(summary = "피드백 삭제 API")
    @DeleteMapping("/feedback/{feedbackId}")
    ResponseEntity<?> deleteFeedback(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                      @Parameter(description = "피드백 아이디")
                                      @Range(min = 1, max = Long.MAX_VALUE, message = "feedbackId 값이 범위를 벗어납니다")
                                      @PathVariable Long feedbackId){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

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
                                     @Parameter(description = "프로젝트 아이디")
                                     @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                     @PathVariable Long projectId,
                                      @Parameter(description = "버전 업 여부")
                                      @RequestParam(defaultValue = "true") boolean versionUp){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        List<FeedbackInfoRes> res = null;
        try {
            res = projectsService.getFeedbackList(projectId, userId, versionUp);
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
                                     @Parameter(description = "피드백 정보") @Valid @RequestBody FeedbackUpdateReq req){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

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
                                     @Parameter(description = "피드백 정보") @Valid @RequestBody FeedbackInsertReq req){
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
    ResponseEntity<?> getProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN, required = false) String accessToken,
                                     @Parameter(description = "프로젝트 아이디")
                                     @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                     @PathVariable Long projectId) {
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
                                       @Parameter(description = "피드백 아이디")
                                       @Range(min = 1, max = Long.MAX_VALUE, message = "feedbackId 값이 범위를 벗어납니다")
                                       @PathVariable Long feedbackId){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = -1;
        try {
            res = projectsService.feedbackComplain(feedbackId, userId);
        } catch(ResponseStatusException e){
            log.warn(e.getReason());
            return Response.makeResponse(e.getStatus(), e.getReason());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res < 0) return Response.notFound("프로젝트 피드백 신고 실패");
        return Response.ok("프로젝트 피드백 신고 성공");
    }

    @Operation(summary = "프로젝트 목록 조회 API")
    @GetMapping
    ResponseEntity<?> getProjectList(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                     @Parameter(description = "정렬 기준")
                                     @Pattern(regexp = "createdDate|likeCnt|feedbackCnt", message = "sort 값은 createdDate, likeCnt, feedbackCnt 중 하나여야 합니다")
                                     @RequestParam(defaultValue = "createdDate") String sort,
                                     @Parameter(description = "페이지 수")
                                     @Min(value = 0, message = "page값은 0이상이어야 합니다")
                                     @RequestParam(defaultValue = "0") int page,
                                     @Parameter(description = "한 페이지에 담기는 개수")
                                     @Positive(message = "size값은 1이상이어야 합니다")
                                     @RequestParam(defaultValue = "10") int size,
                                     @Parameter(description = "검색어") @RequestParam(defaultValue = "") String keyword,
                                     @Parameter(description = "태그 아이디 리스트") @RequestParam(defaultValue = "") String tagIdList,
                                     @Parameter(description = "닫힘 포함 여부(0: 닫힘 미포함, 1: 닫힘 포함)")
                                     @Range(min = 0, max = 1, message = "closed 값은 0 또는 1이어야합니다")
                                     @RequestParam(defaultValue = "0") int closed) {
        Long userId = (accessToken != null)? jwtTokenProvider.getId(accessToken) : 0L;

        Pair<List<ProjectInfoRes>, Boolean> res;
        List<ProjectInfoRes> projectRes = new ArrayList<>();
        try{
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
            res = projectsService.getProjectList(userId, sort, pageRequest, keyword, tagIdList, closed);
            projectRes = res.getLeft();
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nextPage", (res.getRight())? page + 1 : -1);
        resultMap.put("list", projectRes);
        return Response.makeResponse(HttpStatus.OK, "프로젝트 목록 조회 성공", projectRes.size(), resultMap);
    }

    @Operation(summary = "프로젝트 삭제 API")
    @DeleteMapping("/{projectId}")
    ResponseEntity<?> deleteProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "프로젝트 아이디")
                                    @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                    @PathVariable Long projectId){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = 0;
        try{
            res = projectsService.putExpireDateProject(projectId, userId);
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
                                     @Parameter(description = "프로젝트 아이디")
                                     @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                     @PathVariable Long projectId,
                                     @Parameter(description = "연결할 코드 아이디 리스트")
                                     @EachPositive(message = "코드 아이디 리스트 안의 값은 1 이상이어야 합니다")
                                     @RequestBody List<Long> req){
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");


        String key = "insertProject" + userId; // 사용자 ID를 포함한 키 생성

        // 존재하는지 확인
        String isSet = redisTemplate.opsForValue().get(key);
        // SETNX 명령어를 사용하여 키가 존재하지 않을 경우만 실행
        redisTemplate.opsForValue().set(key, "lock", Duration.ofSeconds(1));

        if (isSet == null) { // 키가 존재 안함
            if (req == null) {
                req = new ArrayList<>();
                log.info("코드 연결 전체 해제");
            }

            int res = -1;
            try {
                res = projectsService.connect(projectId, userId, req);
            } catch (Exception e) {
                log.error(e.getMessage());
                return Response.badRequest(e.getMessage());
            }

            if(res < 0) return Response.notFound("프로젝트 코드 연결 실패");
            else return Response.makeResponse(HttpStatus.OK, "프로젝트 코드 연결 성공", 1, res);
        } else {
            // 이미 다른 요청이 처리 중인 경우
            return Response.makeResponse(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다");
        }
    }

    @Operation(summary = "프로젝트 수정 API")
    @PutMapping("/content")
    ResponseEntity<?> updateProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @Parameter(description = "프로젝트 정보 값", required = true) @Valid @RequestBody ProjectReq req) {
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

    @Operation(summary = "이미지 삭제 API")
    @DeleteMapping(value = "/{projectId}/image")
    ResponseEntity<?> deleteImage(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                  @Parameter(description = "삭제할 이미지의 project id", required = true)
                                  @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                  @PathVariable Long projectId,
                                  @Parameter(description = "삭제할 이미지", required = true)
                                  @Pattern(regexp = "^https://d2swdwg2kwda2j.cloudfront.net/.*", message = "url 형식이 유효하지 않습니다")
                                  @RequestBody String imgUrl) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        int res = 0;
        try{
            res = projectsService.deleteImage(imgUrl, projectId, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res <= 0) return Response.notFound("이미지 삭제 실패");
        return Response.makeResponse(HttpStatus.OK, "이미지 삭제 성공", 1, res);
    }

    @Operation(summary = "이미지 등록 API")
    @PostMapping(value = "/{projectId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> insertImage(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                             @Parameter(description = "등록할 project id", required = true)
                                             @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                             @PathVariable Long projectId,
                                             @Parameter(description = "등록할 이미지", required = true)
                                             @NotNull(message = "이미지가 등록되어있지 않습니다")
                                             @RequestPart("image") MultipartFile image) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        String res = null;
        try{
            res = projectsService.insertImage(image, projectId, userId);
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.badRequest(e.getMessage());
        }

        if(res == null) return Response.notFound("이미지 등록 실패");
        return Response.makeResponse(HttpStatus.OK, "이미지 등록 성공", 1, res);
    }

    @Operation(summary = "프로젝트 썸네일 등록/수정 API")
    @PostMapping(value = "/{projectId}/thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> updateProjectThumbnail(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                             @Parameter(description = "등록할 project id", required = true)
                                             @Range(min = 1, max = Long.MAX_VALUE, message = "projectId 값이 범위를 벗어납니다")
                                             @PathVariable Long projectId,
                                             @Parameter(description = "등록할 썸네일", required = true)
                                             @NotNull(message = "썸네일이 등록되어있지 않습니다")
                                             @RequestPart("thumbnail") MultipartFile thumbnail) {

        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");



        String key = "updateProjectThumbnail" + userId; // 사용자 ID를 포함한 키 생성

        // 존재하는지 확인
        String isSet = redisTemplate.opsForValue().get(key);
        // SETNX 명령어를 사용하여 키가 존재하지 않을 경우만 실행
        redisTemplate.opsForValue().set(key, "lock", Duration.ofSeconds(1));

        if (isSet == null) { // 키가 존재 안함
            int res = 0;
            try{
                res = projectsService.updateProjectThumbnail(thumbnail, projectId, userId);
            } catch (Exception e){
                log.error(e.getMessage());
                return Response.badRequest(e.getMessage());
            }

            if(res <= 0) return Response.notFound("프로젝트 썸네일 등록 실패");
            return Response.ok("프로젝트 썸네일 등록 성공");
        } else {
            // 이미 다른 요청이 처리 중인 경우
            return Response.makeResponse(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다");
        }
    }

    @Operation(summary = "프로젝트 정보 등록 API")
    @PostMapping("/content")
    ResponseEntity<?> insertProject(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                     @Parameter(description = "프로젝트 정보 값", required = true)@Valid @RequestBody ProjectReq req) {
        Long userId = jwtTokenProvider.getId(accessToken);
        if(userId <= 0)
            return Response.badRequest("일치하는 유저가 존재하지 않습니다");

        String key = "insertProject" + userId; // 사용자 ID를 포함한 키 생성

        // 존재하는지 확인
        String isSet = redisTemplate.opsForValue().get(key);
        // SETNX 명령어를 사용하여 키가 존재하지 않을 경우만 실행
        redisTemplate.opsForValue().set(key, "lock", Duration.ofSeconds(1));

        if (isSet == null) { // 키가 존재 안함
            Long res = 0L;
            try{
                res = projectsService.insertProject(req, userId);
            } catch (Exception e){
                log.error(e.getMessage());
                return Response.badRequest(e.getMessage());
            }

            if(res <= 0) return Response.notFound("프로젝트 정보 등록 실패");
            return Response.makeResponse(HttpStatus.OK, "프로젝트 정보 등록 성공", 1, res);
        } else {
            // 이미 다른 요청이 처리 중인 경우
            return Response.makeResponse(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다");
        }
    }
}