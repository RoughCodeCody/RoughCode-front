package com.cody.roughcode.user.repository;

import com.cody.roughcode.project.entity.Projects;
import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsersId(Long id);
}
