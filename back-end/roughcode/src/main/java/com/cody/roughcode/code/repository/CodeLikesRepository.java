package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeLikes;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CodeLikesRepository extends JpaRepository<CodeLikes, Long> {

    CodeLikes findByCodesAndUsers(Codes code, Users user);

    @Modifying
    @Transactional
    @Query("delete from CodeLikes c where c.codes.codesId = :codesId")
    void deleteAllByCodesId(@Param("codesId") Long codesId);

    @Modifying
    @Transactional
    @Query("delete from CodeLikes c where c.codes IN :codesList")
    void deleteAllByCodesList(@Param("codesList") List<Codes> codesList);

}
