package com.cody.roughcode.project.service;

import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.user.entity.Users;

public interface ProjectsService {
    int insertProject(ProjectReq req, Long usersId);
}
