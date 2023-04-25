package com.cody.roughcode.project.repository;

import com.cody.roughcode.project.entity.ProjectSelectedTags;
import com.cody.roughcode.project.entity.Projects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectSelectedTagsRepository extends JpaRepository<ProjectSelectedTags, Long> {
    @Query("SELECT ps.projects FROM ProjectSelectedTags ps WHERE ps.tags.tagsId IN :tagIds AND" +
            " (LOWER(ps.projects.title) LIKE %:keyword% OR LOWER(ps.projects.introduction) LIKE %:keyword%)")
    Page<Projects> findAllByKeywordAndTag(@Param("keyword") String keyword, @Param("tagIds") List<Long> tagIds, Pageable pageable);

    @Query("SELECT ps.projects FROM ProjectSelectedTags ps WHERE ps.tags.tagsId IN :tagIds AND ps.projects.closed = false " +
            "AND (LOWER(ps.projects.title) LIKE %:keyword% OR LOWER(ps.projects.introduction) LIKE %:keyword%)")
    Page<Projects> findAllOpenedByKeywordAndTag(@Param("keyword") String keyword, @Param("tagIds") List<Long> tagIds, Pageable pageable);

}
