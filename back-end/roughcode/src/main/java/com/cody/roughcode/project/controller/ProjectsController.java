package com.cody.roughcode.project.controller;

import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.service.ProjectsServiceImpl;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import com.cody.roughcode.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cody.roughcode.jwt.JwtUtil;
import org.springframework.web.multipart.MultipartFile;

import static com.cody.roughcode.jwt.JwtProperties.TOKEN_HEADER;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectsController {
    private final JwtUtil jwtUtil;
    private final ProjectsServiceImpl projectsService;

    @Operation(summary = "프로젝트 등록 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> insertProject(HttpServletRequest request,
                                     @Parameter(description = "프로젝트 정보", required = true) @RequestBody ProjectReq req) {
//        Long userId = jwtUtil.getUserId(request.getHeader(TOKEN_HEADER));
//        Long userId = jwtUtil.getUserId("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLqs6DsiJgiLCJ1c2VySWQiOjEsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2ODA0OTYwMTd9.UyqF0ScQIgOs-npVcjaPGzAAfsWLmUmhXsDaLuprCvA");
        Long userId = 1L;

        int res = 0;
        try{
            res = projectsService.insertProject(req, userId);
        } catch (Exception e){
            log.error(e.getMessage());
        }

        if(res == 0) return Response.notFound("프로젝트 등록 실패");
        return Response.ok("프로젝트 등록 성공");
    }
}