package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.dto.req.ProjectSearchReq;
import com.cody.roughcode.project.dto.res.ProjectDetailRes;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectsService {
    Long insertProject(ProjectReq req, Long usersId);
    int updateProjectThumbnail(MultipartFile thumbnail, Long projectsId, Long usersId);
    int updateProject(ProjectReq req, Long usersId);
    int connect(Long projectsId, Long usersId, List<Long> codesIdList);
    int deleteProject(Long projectsId, Long usersId);
    List<ProjectInfoRes> getProjectList(String sort, PageRequest pageRequest, ProjectSearchReq req);
    ProjectDetailRes getProject(Long projectId, Long usersId);
}
