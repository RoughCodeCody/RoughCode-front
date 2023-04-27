package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.project.entity.CodeFavorites;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeFavoritesRepository extends JpaRepository<CodeFavorites, Long> {

    CodeFavorites findByCodesAndUsers(Codes code, Users user);

}
