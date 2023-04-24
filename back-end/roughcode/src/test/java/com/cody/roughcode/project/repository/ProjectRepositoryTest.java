package com.cody.roughcode.project.repository;

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
    private ProjectsRepository projectRepository;
    @Autowired
    private ProjectsInfoRepository projectInfoRepository;
    @Autowired
    private UsersRepository usersRepository;

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
