package com.nevdev.witcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevdev.witcher.core.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String value);

    User findByUsernameAndPassword(String username, String password);

    User findByUsernameOrEmail(String username, String email);
}
