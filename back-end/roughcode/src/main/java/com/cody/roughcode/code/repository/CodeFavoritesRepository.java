package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.code.entity.CodeFavorites;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CodeFavoritesRepository extends JpaRepository<CodeFavorites, Long> {

    CodeFavorites findByCodesAndUsers(Codes code, Users user);

    @Query("select c from CodeFavorites c where c.codes.codesId = :codesId and c.users.usersId = :usersId")
    CodeFavorites findByCodesIdAndUsersId(@Param("codesId") Long codesId, @Param("usersId") Long usersId);

    @Modifying
    @Transactional
    @Query("delete from CodeFavorites c where c.codes.codesId = :codesId")
    void deleteAllByCodesId(@Param("codesId") Long codesId);
}
