package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.req.ProjectReq;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectsService {
    Long insertProject(ProjectReq req, Long usersId);
    int updateProjectThumbnail(MultipartFile thumbnail, Long projectsId, Long usersId);
    int updateProject(ProjectReq req, Long usersId);
}
