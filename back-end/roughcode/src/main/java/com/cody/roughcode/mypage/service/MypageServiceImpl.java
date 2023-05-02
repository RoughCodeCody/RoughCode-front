package com.cody.roughcode.mypage.service;

import com.cody.roughcode.project.dto.res.ProjectInfoRes;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{
    private final ProjectsRepository projectsRepository;
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public Pair<List<ProjectInfoRes>, Boolean> getProjectList(PageRequest pageRequest, Long usersId) {
        findUser(usersId);

        Page<Projects> projectsPage = projectsRepository.findAllByProjectWriter(usersId, pageRequest);

        return Pair.of(getProjectInfoRes(projectsPage), projectsPage.hasNext());
    }

    @Override
    @Transactional
    public Pair<List<ProjectInfoRes>, Boolean> getFavoriteProjectList(PageRequest pageRequest, Long usersId) {
        findUser(usersId);

        Page<Projects> projectsPage = projectsRepository.findAllMyFavorite(usersId, pageRequest);

        return Pair.of(getProjectInfoRes(projectsPage), projectsPage.hasNext());
    }

    @Override
    public Pair<List<ProjectInfoRes>, Boolean> getFeedbackProjectList(PageRequest pageRequest, Long usersId) {
        findUser(usersId);

        Page<Projects> projectsPage = projectsRepository.findAllMyFeedbacks(usersId, pageRequest);

        return Pair.of(getProjectInfoRes(projectsPage), projectsPage.hasNext());
    }

    private void findUser(Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
    }

    private List<ProjectInfoRes> getProjectInfoRes(Page<Projects> projectsPage) {
        List<Projects> projectList = projectsPage.getContent();
        List<ProjectInfoRes> projectInfoRes = new ArrayList<>();
        for (Projects p : projectList) {
            List<String> tagList = getTagNames(p);

            projectInfoRes.add(ProjectInfoRes.builder()
                    .date(p.getModifiedDate())
                    .img(p.getImg())
                    .projectId(p.getProjectsId())
                    .feedbackCnt(p.getFeedbackCnt())
                    .introduction(p.getIntroduction())
                    .likeCnt(p.getLikeCnt())
                    .tags(tagList)
                    .title(p.getTitle())
                    .version(p.getVersion())
                    .closed(p.isClosed())
                    .build()
            );
        }
        return projectInfoRes;
    }

    private static List<String> getTagNames(Projects p) {
        List<String> tagList = new ArrayList<>();
        if(p.getSelectedTags() != null)
            for (ProjectSelectedTags selected : p.getSelectedTags()) {
                tagList.add(selected.getTags().getName());
            }
        return tagList;
    }

}
