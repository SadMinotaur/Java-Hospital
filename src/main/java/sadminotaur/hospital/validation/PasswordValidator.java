package sadminotaur.hospital.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    @Value("${min_password_length}")
    private int minLength;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return UserValidUtils.passwordReq(password, minLength);
    }

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {

    }
}