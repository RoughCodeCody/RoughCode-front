package com.cody.roughcode.user.repository;

import com.cody.roughcode.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByName(String name);

    Optional<Users> findByName(String name);

    Users findByUsersId(Long id);
}