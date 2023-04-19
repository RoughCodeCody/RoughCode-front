package com.cody.roughcode.project.service;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodesRepostiory;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.project.repository.ProjectSelectedTagsRepository;
import com.cody.roughcode.project.repository.ProjectTagsRepository;
import com.cody.roughcode.project.repository.ProjectsInfoRepository;
import com.cody.roughcode.project.repository.ProjectsRepository;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService{

    private final UsersRepository usersRepository;
    private final ProjectsRepository projectsRepository;
    private final ProjectsInfoRepository projectsInfoRepository;
    private final ProjectSelectedTagsRepository projectSelectedTagsRepository;
    private final ProjectTagsRepository projectTagsRepository;
    private final CodesRepostiory codesRepostiory;

    @Override
    @Transactional
    public int insertProject(ProjectReq req, Users user) {
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다.");
        ProjectsInfo info = ProjectsInfo.builder()
                .url(req.getUrl())
                .notice(req.getNotice())
                .build();

        // 새 프로젝트를 생성하는거면 projectNum은 작성자의 projects_cnt + 1
        // 전의 프로젝트를 업데이트하는거면 projectNum은 전의 projectNum과 동일
        Long projectNum;
        int projectVersion;
        if(req.getProjectId() == -1){ // 새 프로젝트 생성
            user.projectsCntUp();
            usersRepository.save(user);

            projectNum = user.getProjectsCnt();
            projectVersion = 1;
        } else {
            Projects original = projectsRepository.findByProjectsId(req.getProjectId());
            if(original == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다.");

            projectNum = original.getNum();
            projectVersion = original.getVersion() + 1;
        }

        List<Codes> codesList = (codesRepostiory.findByCodesId(req.getCodesId()) == null)? new ArrayList<>() : List.of(codesRepostiory.findByCodesId(req.getCodesId()));

        Projects project = Projects.builder()
                .num(projectNum)
                .version(projectVersion)
                .img(req.getImg())
                .introduction(req.getIntroduction())
                .title(req.getTitle())
                .projectWriter(user)
                .projectsCodes(codesList)
                .build();

        // tag 등록
        for(Long id : req.getSelectedTagsId()){
            projectSelectedTagsRepository.save(ProjectSelectedTags.builder()
                            .tags(projectTagsRepository.findByTagsId(id))
                            .projects(project)
                            .build());
        }

        try {
            Projects savedProject = projectsRepository.save(project);
            info.setProjects(savedProject);
            projectsInfoRepository.save(info);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new SaveFailedException("저장에 실패하였습니다.");
        }

        return 1;
    }
}
