package com.cody.roughcode.project.service;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodesRepostiory;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService{

    private final S3FileServiceImpl s3FileService;

    private final UsersRepository usersRepository;
    private final ProjectsRepository projectsRepository;
    private final ProjectsInfoRepository projectsInfoRepository;
    private final ProjectSelectedTagsRepository projectSelectedTagsRepository;
    private final ProjectTagsRepository projectTagsRepository;
    private final CodesRepostiory codesRepository;

    @Override
    @Transactional
    public int insertProject(ProjectReq req, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다.");
        ProjectsInfo info = ProjectsInfo.builder()
                .url(req.getUrl())
                .notice(req.getNotice())
                .build();

        // 새 프로젝트를 생성하는거면 projectNum은 작성자의 projects_cnt + 1
        // 전의 프로젝트를 업데이트하는거면 projectNum은 전의 projectNum과 동일
        Long projectNum;
        int projectVersion;
        int likeCnt = 0;
        if(req.getProjectId() == -1){ // 새 프로젝트 생성
            user.projectsCntUp();
            usersRepository.save(user);

            projectNum = user.getProjectsCnt();
            projectVersion = 1;
        } else { // 기존 프로젝트 버전 업
            Projects original = projectsRepository.findProjectWithMaxVersionByProjectsId(req.getProjectId());
            if(original == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다.");

            projectNum = original.getNum();
            projectVersion = original.getVersion() + 1;
            likeCnt = original.getLikeCnt();
        }

        MultipartFile thumbnail = req.getThumbnail();
        if(thumbnail == null) throw new NullPointerException("썸네일이 등록되어있지 않습니다");

        List<String> fileNames = List.of(String.valueOf(projectNum), String.valueOf(projectVersion));

        try {
            String imgUrl = s3FileService.upload(thumbnail, "project", fileNames);

            List<Codes> codesList = (codesRepository.findByCodesId(req.getCodesId()) == null)? new ArrayList<>() : List.of(codesRepository.findByCodesId(req.getCodesId()));

            Projects project = Projects.builder()
                    .num(projectNum)
                    .version(projectVersion)
                    .img(imgUrl)
                    .introduction(req.getIntroduction())
                    .title(req.getTitle())
                    .projectWriter(user)
                    .projectsCodes(codesList)
                    .likeCnt(likeCnt)
                    .build();
            Projects savedProject = projectsRepository.save(project);

            // tag 등록
            for(Long id : req.getSelectedTagsId()){
                ProjectTags projectTag = projectTagsRepository.findByTagsId(id);
                projectSelectedTagsRepository.save(ProjectSelectedTags.builder()
                                .tags(projectTag)
                                .projects(project)
                                .build());

                projectTag.cntUp();
                projectTagsRepository.save(projectTag);
            }

            info.setProjects(savedProject);
            projectsInfoRepository.save(info);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new SaveFailedException("저장에 실패하였습니다.");
        }

        return 1;
    }
}
