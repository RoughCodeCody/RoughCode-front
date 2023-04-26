package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodesRepostiory extends JpaRepository<Codes, Long> {
    Codes findByCodesId(Long id);
}
