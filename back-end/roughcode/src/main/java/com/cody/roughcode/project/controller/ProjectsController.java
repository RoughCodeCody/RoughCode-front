package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
import com.cody.roughcode.security.auth.JwtProperties;
import com.cody.roughcode.security.auth.JwtTokenProvider;
import com.cody.roughcode.util.Response;
import io.lettuce.core.ScriptOutputType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import static com.cody.roughcode.security.auth.JwtProperties.TOKEN_HEADER;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectsController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectsServiceImpl projectsService;

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