package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeLanguages;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeLanguagesRepository extends JpaRepository<CodeLanguages, Long> {
    CodeLanguages findByLanguagesId(Long id);

    List<CodeLanguages> findAllByNameContaining(String name, Sort sort);
}
