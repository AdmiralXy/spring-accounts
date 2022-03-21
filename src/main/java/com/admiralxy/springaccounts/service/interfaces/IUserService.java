package com.admiralxy.springaccounts.service.interfaces;

import com.admiralxy.springaccounts.entity.User;
import java.util.List;

public interface IUserService {
    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    void delete(Long id);
    User save(User user);
}
