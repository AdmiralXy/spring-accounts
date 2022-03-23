package com.admiralxy.springaccounts.repository;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    List<Credential> getAllByUser(User user);
}