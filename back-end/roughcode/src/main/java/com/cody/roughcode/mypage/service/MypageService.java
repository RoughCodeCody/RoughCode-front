package com.cody.roughcode.mypage.service;

import com.cody.roughcode.code.dto.res.CodeInfoRes;
import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MypageService {
    String makeStatCard(String userName);

    Pair<List<CodeInfoRes>, Boolean> getCodeList(PageRequest pageRequest, Long usersId);
    Pair<List<CodeInfoRes>, Boolean> getFavoriteCodeList(PageRequest pageRequest, Long usersId);
    Pair<List<CodeInfoRes>, Boolean> getReviewCodeList(PageRequest pageRequest, Long usersId);

    Pair<List<ProjectInfoRes>, Boolean> getProjectList(PageRequest pageRequest, Long usersId);
    Pair<List<ProjectInfoRes>, Boolean> getFavoriteProjectList(PageRequest pageRequest, Long usersId);
    Pair<List<ProjectInfoRes>, Boolean> getFeedbackProjectList(PageRequest pageRequest, Long usersId);
}
