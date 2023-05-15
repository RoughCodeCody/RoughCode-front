package com.cody.roughcode.project.repository;

import com.cody.roughcode.code.entity.CodeSelectedTags;
import com.cody.roughcode.code.entity.CodeTags;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodeSelectedTagsRepository;
import com.cody.roughcode.code.repository.CodeTagsRepository;
import com.cody.roughcode.code.repository.CodesRepository;
import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.ProjectTags;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 기본적으로 인메모리 데티어베이스인 H2 기반으로 테스트용 데이터베이스를 구축, 테스트가 끝나면 트랜잭션 롤백
//  각각의 테스트 메서드가 실행될 때마다 Spring 컨텍스트를 제거하고 데이터베이스를 초기화합니다. 이렇게 하면 테스트 간에 독립성을 유지하면서 테스트를 실행할 수 있습니다.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectRepositoryTest {

    final Users users = Users.builder()
            .usersId(1L)
            .email("kosy1782@gmail.com")
            .name("고수")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();
    final Users users2 = Users.builder()
            .usersId(2L)
            .email("kosy17822@gmail.com")
            .name("고수2")
            .roles(List.of(String.valueOf(ROLE_USER)))
            .build();

    @Autowired
    private CodesRepository codesRepository;
    @Autowired
    private ProjectsRepository projectRepository;
    @Autowired
    private ProjectsInfoRepository projectInfoRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProjectSelectedTagsRepository projectSelectedTagsRepository;
    @Autowired
    private ProjectTagsRepository projectTagsRepository;
    @Autowired
    private CodeTagsRepository codeTagsRepository;
    @Autowired
    private CodeSelectedTagsRepository codeSelectedTagsRepository;

    List<ProjectTags> projectTagsInit(){
        List<ProjectTags> tagsList = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            tagsList.add(ProjectTags.builder()
                    .tagsId(i)
                    .name("tag" + i)
                    .build());
        }

        return tagsList;
    }
    List<CodeTags> codeTagsInit(){
        List<CodeTags> tagsList = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            tagsList.add(CodeTags.builder()
                    .tagsId(i)
                    .name("tag" + i)
                    .build());
        }

        return tagsList;
    }

    @DisplayName("내가 작성한 프로젝트 총 개수")
    @Test
    void countProjectByProjectWriter(){
        // given
        usersRepository.save(users);
        usersRepository.save(users2);
        Projects project1 = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro1")
                .title("title")
                .projectWriter(users)
                .closed(true)
                .likeCnt(33)
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(2)
                .img("image url2")
                .introduction("intro2")
                .title("title2")
                .projectWriter(users)
                .closed(false)
                .likeCnt(11)
                .build();
        Projects project3 = Projects.builder()
                .projectsId(3L)
                .num(1L)
                .version(1)
                .img("image url3")
                .introduction("intro3")
                .title("title3")
                .projectWriter(users2)
                .closed(false)
                .likeCnt(22)
                .build();
        Projects project4 = Projects.builder()
                .projectsId(4L)
                .num(2L)
                .version(1)
                .img("image url4")
                .introduction("intro4")
                .title("title4")
                .projectWriter(users2)
                .closed(true)
                .likeCnt(4554)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);
        projectRepository.save(project4);


        // when
        List<Projects> projects = projectRepository.findAll();
        int count = projectRepository.countByProjectWriter(users);

        // then
        assertThat(projects.size()).isEqualTo(4);
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("프로젝트 목록 가져오기 - 닫혀있는 것 포함 최신순")
    @Test
    void getProjectListNew(){
        // given
        usersRepository.save(users);
        Projects project1 = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro1")
                .title("title")
                .projectWriter(users)
                .closed(true)
                .likeCnt(33)
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(2)
                .img("image url2")
                .introduction("intro2")
                .title("title2")
                .projectWriter(users)
                .closed(false)
                .likeCnt(11)
                .build();
        Projects project3 = Projects.builder()
                .projectsId(3L)
                .num(1L)
                .version(3)
                .img("image url3")
                .introduction("intro3")
                .title("title3")
                .projectWriter(users)
                .closed(false)
                .likeCnt(22)
                .build();
        Projects project4 = Projects.builder()
                .projectsId(4L)
                .num(1L)
                .version(4)
                .img("image url4")
                .introduction("intro4")
                .title("title4")
                .projectWriter(users)
                .closed(true)
                .likeCnt(4554)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project3);
        projectRepository.save(project4);
        projectRepository.save(project2);

        // when
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Projects> projectsList = projectRepository.findAllByKeyword("", pageRequest);
        List<Projects> projects = projectsList.getContent();

        for(var p : projects){
            System.out.println(p.getIntroduction());
        }

        // then
        assertThat(projects.size()).isEqualTo(1);
    }

    @DisplayName("프로젝트 목록 가져오기 - 닫혀있는 것 포함 좋아요순")
    @Test
    void getProjectListLike(){
        // given
        usersRepository.save(users);
        Projects project1 = Projects.builder()
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .closed(true)
                .likeCnt(33)
                .build();
        Projects project2 = Projects.builder()
                .num(2L)
                .version(1)
                .img("image url2")
                .introduction("intro2")
                .title("title2")
                .projectWriter(users)
                .closed(false)
                .likeCnt(11)
                .build();
        Projects project3 = Projects.builder()
                .num(2L)
                .version(2)
                .img("image url3")
                .introduction("intro3")
                .title("title3")
                .projectWriter(users)
                .closed(false)
                .likeCnt(22)
                .build();
        Projects project4 = Projects.builder()
                .num(2L)
                .version(3)
                .img("image url4")
                .introduction("intro4")
                .title("title4")
                .projectWriter(users)
                .closed(true)
                .likeCnt(4554)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);
        projectRepository.save(project4);

        // when
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCnt"));
        Page<Projects> projectsList = projectRepository.findAllByKeyword("title", pageRequest);
        List<Projects> projects = projectsList.getContent();

        // then
        assertThat(projects.size()).isEqualTo(2);
        assertThat(projects.get(0).getProjectsId()).isEqualTo(4L);
        assertThat(projects.get(1).getProjectsId()).isEqualTo(1L);
    }

    @DisplayName("프로젝트 목록 가져오기 - 닫혀있는 것 포함 피드백순")
    @Test
    void getProjectListFeedback(){
        // given
        usersRepository.save(users);
        Projects project1 = Projects.builder()
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .closed(true)
                .likeCnt(33)
                .feedbackCnt(1231)
                .build();
        Projects project2 = Projects.builder()
                .num(2L)
                .version(1)
                .img("image url2")
                .introduction("intro2")
                .title("title2")
                .projectWriter(users)
                .closed(false)
                .likeCnt(11)
                .feedbackCnt(23)
                .build();
        Projects project3 = Projects.builder()
                .num(3L)
                .version(1)
                .img("image url3")
                .introduction("intro3")
                .title("title3")
                .projectWriter(users)
                .closed(false)
                .likeCnt(22)
                .feedbackCnt(0)
                .build();
        Projects project4 = Projects.builder()
                .num(4L)
                .version(1)
                .img("image url4")
                .introduction("intro4")
                .title("title4")
                .projectWriter(users)
                .closed(true)
                .likeCnt(4554)
                .feedbackCnt(12)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project3);
        projectRepository.save(project4);
        projectRepository.save(project2);

        // when
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "feedbackCnt"));
        Page<Projects> projectsList = projectRepository.findAllByKeyword("", pageRequest);
        List<Projects> projects = projectsList.getContent();

        // then
        assertThat(projects.size()).isEqualTo(4);
        assertThat(projects.get(0)).isEqualTo(project1);
        assertThat(projects.get(1)).isEqualTo(project2);
        assertThat(projects.get(2)).isEqualTo(project4);
        assertThat(projects.get(3)).isEqualTo(project3);
    }

    @DisplayName("프로젝트 목록 가져오기 - 닫혀있는 것 미포함 피드백순")
    @Test
    void getProjectListOpenedFeedback(){
        // given
        usersRepository.save(users);
        Projects project1 = Projects.builder()
                .projectsId(1L)
                .num(2L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .closed(false)
                .likeCnt(33)
                .feedbackCnt(1231)
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(1)
                .img("image url2")
                .introduction("intro2")
                .title("title2")
                .projectWriter(users)
                .closed(true)
                .likeCnt(11)
                .feedbackCnt(23)
                .build();
        Projects project3 = Projects.builder()
                .projectsId(3L)
                .num(1L)
                .version(2)
                .img("image url3")
                .introduction("intro3")
                .title("title3")
                .projectWriter(users)
                .closed(true)
                .likeCnt(22)
                .feedbackCnt(0)
                .build();
        Projects project4 = Projects.builder()
                .projectsId(4L)
                .num(1L)
                .version(3)
                .img("image url4")
                .introduction("intro4")
                .title("title4")
                .projectWriter(users)
                .closed(false)
                .likeCnt(4554)
                .feedbackCnt(12)
                .build();

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);
        projectRepository.save(project4);

        // when
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "feedbackCnt"));
        Page<Projects> projectsList = projectRepository.findAllOpenedByKeyword("", pageRequest);
        List<Projects> projects = projectsList.getContent();

        // then
        assertThat(projects.size()).isEqualTo(2);
        assertThat(projects.get(0).getProjectsId()).isEqualTo(1L);
        assertThat(projects.get(1).getProjectsId()).isEqualTo(4L);
    }

    @DisplayName("프로젝트 삭제하기")
    @Test
    void deleteProject(){
        // given
        usersRepository.save(users);
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        projectRepository.save(project);
        List<ProjectTags> projectTagList = projectTagsInit();
        List<ProjectSelectedTags> projectSelectedTagsList = new ArrayList<>();
        for (ProjectTags tag : projectTagList) {
            projectTagsRepository.save(tag);
            projectSelectedTagsList.add(
                    ProjectSelectedTags.builder()
                            .projects(project)
                            .selectedTagsId(tag.getTagsId())
                            .tags(tag)
                            .build()
            );
            projectSelectedTagsRepository.save(projectSelectedTagsList.get(projectSelectedTagsList.size() - 1));
        }
        Codes code = Codes.builder()
                .codesId(1L)
                .num(1L)
                .version(1)
                .codeWriter(users)
                .title("title")
                .build();
        codesRepository.save(code);
        List<CodeTags> codeTagList = codeTagsInit();
        for (CodeTags tag : codeTagList) {
            codeTagsRepository.save(tag);
            codeSelectedTagsRepository.save(
                    CodeSelectedTags.builder()
                            .codes(code)
                            .selectedTagsId(tag.getTagsId())
                            .tags(tag)
                            .build()
            );
        }

        code.setProject(project);
        codesRepository.save(code);
        project.setCodes(List.of(code));
        projectRepository.save(project);

        // when
        project.setCodes(null);
        projectRepository.save(project);
        code.setProject(null);
        codesRepository.save(code);

        // 카운트 다운 후 제거
        projectSelectedTagsRepository.deleteAll(projectSelectedTagsList);
        projectRepository.delete(project);

        // then
        Projects deleted = projectRepository.findByProjectsIdAndExpireDateIsNull(1L);
        assertThat(deleted).isEqualTo(null);
    }

    @DisplayName("프로젝트 Num 가져오기")
    @Test
    void getProjectNum(){
        // given
        usersRepository.save(users);
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        projectRepository.save(project);

        // when
        Projects savedProjects = projectRepository.findByProjectsIdAndExpireDateIsNull(1L);

        // then
        assertThat(project.getNum()).isEqualTo(savedProjects.getNum());
    }

    @DisplayName("프로젝트 Num과 User에 해당하는 Max Version 가져오기")
    @Test
    void getMaxProjectVersion(){
        // given
        usersRepository.save(users);
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        Projects project2 = Projects.builder()
                .projectsId(2L)
                .num(1L)
                .version(2)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();
        projectRepository.save(project);
        projectRepository.save(project2);

        // when
        Projects original = projectRepository.findLatestProject(1L, 1L);

        // then
        assertThat(original).isEqualTo(project2);
    }

    @DisplayName("프로젝트 등록")
    @Test
    void insertProject(){
        // given
        usersRepository.save(users);
        ProjectsInfo info = ProjectsInfo.builder()
                .id(1L)
                .url("url")
                .notice("notice")
                .build();
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(1L)
                .version(1)
                .img("image url")
                .introduction("intro")
                .title("title")
                .projectWriter(users)
                .build();

        // when
        projectRepository.save(project);
        Projects savedProject = projectRepository.findByProjectsIdAndExpireDateIsNull(project.getProjectsId());
        info.setProjects(savedProject);
        ProjectsInfo savedProjectInfo = projectInfoRepository.save(info);

        // then
        assertThat(savedProject.getImg()).isEqualTo(project.getImg());
        assertThat(savedProject.getIntroduction()).isEqualTo(project.getIntroduction());
        assertThat(savedProject.getTitle()).isEqualTo(project.getTitle());

        ProjectsInfo getInfo = projectInfoRepository.findByProjects(savedProject);
        assertThat(getInfo.getUrl()).isEqualTo(info.getUrl());
        assertThat(getInfo.getContent()).isEqualTo(info.getContent());
        assertThat(getInfo.getNotice()).isEqualTo(info.getNotice());
    }
}
