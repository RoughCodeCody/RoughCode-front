package com.cody.roughcode.code.repository;

import com.cody.roughcode.code.entity.CodeLikes;
import com.cody.roughcode.code.entity.Codes;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeLikesRepository extends JpaRepository<CodeLikes, Long> {

    CodeLikes findByCodesAndUsers(Codes code, Users user);

}
