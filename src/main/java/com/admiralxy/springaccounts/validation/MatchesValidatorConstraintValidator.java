package com.admiralxy.springaccounts.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchesValidatorConstraintValidator implements ConstraintValidator<Matches, Object> {

    private String field;
    private String verifyField;

    public void initialize(Matches matches) {
        this.field = matches.field();
        this.verifyField = matches.verifyField();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            String fieldValue = (String) wrapper.getPropertyValue(field);
            String verifyFieldValue = (String) wrapper.getPropertyValue(verifyField);
            boolean valid = (fieldValue == null) && (verifyFieldValue == null);
            if (valid) {
                return true;
            }

            boolean match = (fieldValue != null) && fieldValue.equals(verifyFieldValue);
            if (!match) {
                String messageTemplate = context.getDefaultConstraintMessageTemplate();
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(messageTemplate)
                        .addNode(verifyField)
                        .addConstraintViolation();
            }
            return match;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
