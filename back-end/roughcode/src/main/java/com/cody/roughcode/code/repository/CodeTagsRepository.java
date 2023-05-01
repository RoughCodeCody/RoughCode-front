package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeTagsRepository extends JpaRepository<CodeTags, Long> {
    CodeTags findByTagsId(Long id);

    List<CodeTags> findByTagsIdIn(List<Long> ids);
}
