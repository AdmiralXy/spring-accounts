package com.admiralxy.springaccounts.repository;

import com.admiralxy.springaccounts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}