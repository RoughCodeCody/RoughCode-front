package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectFavorites;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Projects findByProjectsId(Long id);

    @Query(value = "SELECT p FROM Projects p WHERE p.num = :num AND p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE p2.num = :num AND p2.projectWriter.usersId = :userId) AND p.projectWriter.usersId = :userId")
    Projects findLatestProject(@Param("num") Long num, @Param("userId") Long userId);

    @Query("SELECT p FROM Projects p WHERE p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE (p2.num = p.num AND p2.projectWriter = p.projectWriter)) " +
            "AND (LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%)")
    Page<Projects> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Projects p WHERE p.closed = false AND p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE (p2.num = p.num AND p2.projectWriter = p.projectWriter)) " +
            "AND (LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%)")
    Page<Projects> findAllOpenedByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Projects> findByNumAndProjectWriter(Long num, Users projectWriter);

    @Query("SELECT p FROM Projects p WHERE p.projectWriter.usersId = :userId AND p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE (p2.num = p.num AND p2.projectWriter = p.projectWriter))")
    Page<Projects> findAllByProjectWriter(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT pf.projects FROM ProjectFavorites pf JOIN pf.projects p WHERE pf.users.usersId = :userId ORDER BY p.modifiedDate DESC")
    Page<Projects> findAllMyFavorite(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT distinct f.projectsInfo.projects FROM Feedbacks f JOIN f.projectsInfo.projects p WHERE f.users.usersId = :userId ORDER BY f.projectsInfo.projects.modifiedDate DESC")
    Page<Projects> findAllMyFeedbacks(@Param("userId") Long userId, Pageable pageable);
}