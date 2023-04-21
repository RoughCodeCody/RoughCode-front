package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.project.entity.ProjectsInfo;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.cody.roughcode.user.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 기본적으로 인메모리 데티어베이스인 H2 기반으로 테스트용 데이터베이스를 구축, 테스트가 끝나면 트랜잭션 롤백
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

    @DisplayName("프로젝트 등록")
    @Test
    void insertProject(){
        // given
        usersRepository.save(users);
        Long project_num = usersRepository.findById(users.getUsersId()).get().getProjectsCnt() + 1;
        ProjectsInfo info = ProjectsInfo.builder()
                .url("url")
                .notice("notice")
                .build();
        Projects project = Projects.builder()
                .projectsId(1L)
                .num(project_num)
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
