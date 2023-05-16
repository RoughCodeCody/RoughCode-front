package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeSelectedTags;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cody.roughcode.code.entity.Codes;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeSelectedTagsRepository extends JpaRepository<CodeSelectedTags, Long> {
    @Query("SELECT c FROM Codes c JOIN c.selectedTags cst WHERE " +
            "c.version = (SELECT MAX(c2.version) FROM Codes c2 WHERE (c2.num = c.num AND c2.codeWriter = c.codeWriter)) AND " +
            "cst.tags.tagsId IN :tagIds " +
            "AND (LOWER(c.title) LIKE %:keyword% OR LOWER(c.codeWriter.name) LIKE %:keyword%) " +
            "AND c.expireDate is NULL " +
            "GROUP BY c.codesId " +
            "HAVING COUNT(DISTINCT cst.tags.tagsId) = :tagIdsSize"
    )
    Page<Codes> findAllByKeywordAndTag(@Param("keyword") String keyword, @Param("tagIds") List<Long> tagIds, @Param("tagIdsSize") Long tagIdsSize, Pageable pageable);

}