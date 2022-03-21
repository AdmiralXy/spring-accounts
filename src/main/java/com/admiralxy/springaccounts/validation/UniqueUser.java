package com.admiralxy.springaccounts.validation;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueUserConstraintValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {

    String message() default "validation.user.username.unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
