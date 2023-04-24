package com.cody.roughcode.project.service;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.Projects;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectsService {
    Long insertProject(ProjectReq req, Long usersId);
    int updateProjectThumbnail(MultipartFile thumbnail, Long projectsId, Long usersId);
    int updateProject(ProjectReq req, Long usersId);
    int connect(Long projectsId, Long usersId, List<Long> codesIdList);
}
