package com.cody.roughcode.project.repository;

import com.cody.roughcode.code.entity.CodeSelectedTags;
import com.cody.roughcode.code.entity.CodeTags;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.repository.CodeSelectedTagsRepository;
import com.cody.roughcode.code.repository.CodeTagsRepository;
import com.cody.roughcode.code.repository.CodesRepostiory;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CodesRepostiory codesRepostiory;
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
        codesRepostiory.save(code);
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
        codesRepostiory.save(code);
        project.setCodes(List.of(code));
        projectRepository.save(project);

        // when
        project.setCodes(null);
        projectRepository.save(project);
        code.setProject(null);
        codesRepostiory.save(code);

        // 카운트 다운 후 제거
        projectSelectedTagsRepository.deleteAll(projectSelectedTagsList);
        projectRepository.delete(project);

        // then
        Projects deleted = projectRepository.findByProjectsId(1L);
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
        Projects savedProjects = projectRepository.findByProjectsId(1L);

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
        Projects savedProject = projectRepository.save(project);
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
