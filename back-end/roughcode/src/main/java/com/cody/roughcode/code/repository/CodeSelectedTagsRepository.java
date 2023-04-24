package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeSelectedTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeSelectedTagsRepository extends JpaRepository<CodeSelectedTags, Long> {
}