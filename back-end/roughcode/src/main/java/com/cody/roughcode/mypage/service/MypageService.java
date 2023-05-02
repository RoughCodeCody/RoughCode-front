package com.cody.roughcode.mypage.service;

import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MypageService {
    Pair<List<ProjectInfoRes>, Boolean> getProjectList(PageRequest pageRequest, Long usersId);
    Pair<List<ProjectInfoRes>, Boolean> getFavoriteProjectList(PageRequest pageRequest, Long usersId);
    Pair<List<ProjectInfoRes>, Boolean> getFeedbackProjectList(PageRequest pageRequest, Long usersId);
}
