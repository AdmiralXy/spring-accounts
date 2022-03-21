package com.admiralxy.springaccounts.validation;

import com.admiralxy.springaccounts.entity.User;
import com.admiralxy.springaccounts.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserConstraintValidator implements ConstraintValidator<UniqueUser, User> {

    private final IUserService userService;

    @Autowired
    public UniqueUserConstraintValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(User person, ConstraintValidatorContext ctx) {
        if (userService.findByUsername(person.getUsername()) != null) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("{validation.user.username.unique}")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
