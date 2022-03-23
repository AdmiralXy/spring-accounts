package com.admiralxy.springaccounts.util;

import com.admiralxy.springaccounts.entity.User;
import com.admiralxy.springaccounts.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    private final IUserService userService;

    @Autowired
    public AuthUtils(IUserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails)
            username = ((UserDetails)principal).getUsername();
        else
            username = principal.toString();
        return userService.findByUsername(username);
    }
}
