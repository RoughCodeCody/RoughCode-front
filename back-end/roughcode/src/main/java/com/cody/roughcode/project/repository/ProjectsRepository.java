package com.cody.roughcode.project.repository;

import com.amazonaws.services.s3.transfer.Copy;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Projects findByProjectsId(Long id);

    @Query(value = "SELECT p FROM Projects p WHERE p.num = :num AND p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE p2.num = :num AND p2.projectWriter.usersId = :userId) AND p.projectWriter.usersId = :userId")
    Projects findLatestProject(@Param("num") Long num, @Param("userId") Long userId);
}
