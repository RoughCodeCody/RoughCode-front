package com.cody.roughcode.project.service;

import com.cody.roughcode.code.repository.CodesRepostiory;
import com.cody.roughcode.exception.NotMatchException;
import com.cody.roughcode.exception.NotNewestVersionException;
import com.cody.roughcode.exception.SaveFailedException;
import com.cody.roughcode.project.dto.req.ProjectReq;
import com.cody.roughcode.project.entity.*;
import com.cody.roughcode.project.repository.*;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final FeedbacksRepository feedbacksRepository;

    @Override
    @Transactional
    public Long insertProject(ProjectReq req, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
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
            // num 가져오기
            // num과 user가 일치하는 max version값 가져오기
            // num과 user와 max version값에 일치하는 project 가져오기
            Projects original = projectsRepository.findByProjectsId(req.getProjectId());
            if(original == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다");
            original = projectsRepository.findLatestProject(original.getNum(), user.getUsersId());
            if(original == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다");

            if(!original.getProjectWriter().equals(user)) throw new NotMatchException();

            projectNum = original.getNum();
            projectVersion = original.getVersion() + 1;
            likeCnt = original.getLikeCnt();
        }

        Long projectId = -1L;
        try {
            Projects project = Projects.builder()
                    .num(projectNum)
                    .version(projectVersion)
                    .img("temp")
                    .introduction(req.getIntroduction())
                    .title(req.getTitle())
                    .projectWriter(user)
                    .likeCnt(likeCnt)
                    .build();
            Projects savedProject = projectsRepository.save(project);
            projectId = savedProject.getProjectsId();

            // tag 등록
            if(req.getSelectedTagsId() != null)
                for(Long id : req.getSelectedTagsId()){
                    ProjectTags projectTag = projectTagsRepository.findByTagsId(id);
                    projectSelectedTagsRepository.save(ProjectSelectedTags.builder()
                            .tags(projectTag)
                            .projects(project)
                            .build());

                    projectTag.cntUp();
                    projectTagsRepository.save(projectTag);
                }
            else log.info("등록한 태그가 없습니다");

            // feedback 선택
            if(req.getSelectedFeedbacksId() != null)
                for(Long id : req.getSelectedFeedbacksId()){
                    Feedbacks feedback = feedbacksRepository.findFeedbacksByFeedbacksId(id);
                    if(feedback == null) throw new NullPointerException("일치하는 피드백이 없습니다");
                    if(!feedback.getProjects().getNum().equals(projectNum))
                        throw new NullPointerException("피드백과 프로젝트가 일치하지 않습니다");
                    feedback.setSelected(true);
                    feedbacksRepository.save(feedback);
                }
            else log.info("선택한 피드백이 없습니다");

            info.setProjects(savedProject);
            projectsInfoRepository.save(info);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new SaveFailedException("프로젝트 정보 저장에 실패하였습니다");
        }

        return projectId;
    }

    @Override
    @Transactional
    public int updateProjectThumbnail(MultipartFile thumbnail, Long projectsId, Long usersId) {
        Users user = usersRepository.findByUsersId(usersId);
        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
        if(thumbnail == null) throw new NullPointerException("썸네일이 등록되어있지 않습니다");
        Projects project = projectsRepository.findByProjectsId(projectsId);
        if(project == null) throw new NullPointerException("일치하는 프로젝트가 없습니다");
        if(!project.getProjectWriter().equals(user)) throw new NotMatchException();
        Projects latestProject = projectsRepository.findLatestProject(project.getNum(), usersId);
        if(!project.equals(latestProject)) throw new NotNewestVersionException();

        Long projectNum = project.getNum();
        int projectVersion = project.getVersion();

        try{
            String fileName = user.getName() + "_" + projectNum + "_" + projectVersion;

            String imgUrl = s3FileService.upload(thumbnail, "project", fileName);

            project.setImgUrl(imgUrl);
            projectsRepository.save(project);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new SaveFailedException("프로젝트 썸네일 저장에 실패하였습니다");
        }

        return 1;
    }

//    @Override
//    public int updateProject(ProjectReq req, MultipartFile thumbnail, Long usersId) {
//        Users user = usersRepository.findByUsersId(usersId);
//        if(user == null) throw new NullPointerException("일치하는 유저가 존재하지 않습니다");
//
//        // 기존의 프로젝트 가져오기
//        Projects original = projectsRepository.findByProjectsId(req.getProjectId());
//        if(original == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다");
//        else if (!original.equals(projectsRepository.findProjectWithMaxVersionByProjectsId(req.getProjectId()))) {
//            throw new NotNewestVersionException("최신 버전이 아닙니다");
//        }
//        ProjectsInfo originalInfo = projectsInfoRepository.findByProjects(original);
//        if(originalInfo == null) throw new NullPointerException("일치하는 프로젝트가 존재하지 않습니다");
//
//        String imgUrl;
//        String fileName = original.getNum() + "_" + original.getVersion();
//
//        try {
//            if(thumbnail == null){ // 썸네일 바뀌지 않는 경우
//                imgUrl = original.getImg();
//            } else{ // 썸네일 바뀌는 경우
//                // S3에서 해당하는 파일 찾아서 삭제하기
//                s3FileService.delete(original.getImg());
//
//                // 새로 등록하기
//                imgUrl = s3FileService.upload(thumbnail, "project", fileName);
//            }
//
//            // tag 삭제
//            List<ProjectSelectedTags> selectedTagsList = original.getSelectedTags();
//            for (ProjectSelectedTags tag : selectedTagsList) {
//                projectSelectedTagsRepository.delete(tag);
//
//                ProjectTags projectTag = tag.getTags();
//                projectTag.cntDown();
//            }
//
//            // tag 등록
//            for(Long id : req.getSelectedTagsId()){
//                ProjectTags projectTag = projectTagsRepository.findByTagsId(id);
//                projectSelectedTagsRepository.save(ProjectSelectedTags.builder()
//                        .tags(projectTag)
//                        .projects(original)
//                        .build());
//
//                projectTag.cntUp();
//                projectTagsRepository.save(projectTag);
//            }
//
//            original.updateProject(req, imgUrl);
//            originalInfo.updateProject(req);
//        } catch(Exception e){
//            log.error(e.getMessage());
//            throw new UpdateFailedException(e.getMessage());
//        }
//
//        return 1;
//    }
}


