package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.req.ProjectReq;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectsService {
    int insertProject(ProjectReq req, Long usersId);
}
