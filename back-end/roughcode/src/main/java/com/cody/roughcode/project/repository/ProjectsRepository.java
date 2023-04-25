package com.cody.roughcode.project.repository;

import com.amazonaws.services.s3.transfer.Copy;
import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Projects findByProjectsId(Long id);

    @Query(value = "SELECT p FROM Projects p WHERE p.num = :num AND p.version = (SELECT MAX(p2.version) FROM Projects p2 WHERE p2.num = :num AND p2.projectWriter.usersId = :userId) AND p.projectWriter.usersId = :userId")
    Projects findLatestProject(@Param("num") Long num, @Param("userId") Long userId);

    @Query("SELECT p FROM Projects p WHERE LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%")
    Page<Projects> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Projects p WHERE p.closed = false AND (LOWER(p.title) LIKE %:keyword% OR LOWER(p.introduction) LIKE %:keyword%)")
    Page<Projects> findAllOpenedByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
