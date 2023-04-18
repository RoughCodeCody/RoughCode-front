package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectId;
import com.cody.roughcode.project.entity.Projects;
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
    private ProjectRepository projectRepository;
    @Autowired
    private UsersRepository usersRepository;

    @DisplayName("프로젝트 등록")
    @Test
    void insertProject(){
        // given
        usersRepository.save(users);
        Projects project = Projects.builder()
                .projectsId(ProjectId.builder().projectId(1L).version(1).build())
                .img("image url")
                .content("content")
                .users(users)
                .notice("notice")
                .title("title")
                .build();

        // when
        Projects savedProject = projectRepository.save(project);

        // then
        assertThat(savedProject.getImg()).isEqualTo(project.getImg());
        assertThat(savedProject.getContent()).isEqualTo(project.getContent());
        assertThat(savedProject.getNotice()).isEqualTo(project.getNotice());
        assertThat(savedProject.getProjectsId().getVersion()).isEqualTo(project.getProjectsId().getVersion());
    }
}
