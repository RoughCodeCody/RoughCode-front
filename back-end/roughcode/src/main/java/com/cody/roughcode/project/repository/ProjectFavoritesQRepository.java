package com.cody.roughcode.project.repository;

import com.cody.roughcode.config.Querydsl4RepositorySupport;
import com.cody.roughcode.project.entity.ProjectFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.cody.roughcode.project.entity.QProjectFavorites.projectFavorites;

@Repository
public class ProjectFavoritesQRepository extends Querydsl4RepositorySupport {
    public ProjectFavoritesQRepository() {
        super(ProjectFavorites.class);
    }

    // SELECT pf.users FROM ProjectFavorites pf WHERE pf.projects = :original AND pf.projects.expireDate IS NULL
    public List<Users> findByProjects(Projects original) {
        return select(projectFavorites.users)
                .from(projectFavorites)
                .where(
                        projectFavorites.projects.eq(original),
                        projectFavorites.projects.expireDate.isNull()
                )
                .fetch();
    }
}