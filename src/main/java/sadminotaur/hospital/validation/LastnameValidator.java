package sadminotaur.hospital.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastnameValidator implements ConstraintValidator<LastnameValidation, String> {

    @Value("${max_name_length}")
    private int maxLength;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return UserValidUtils.nameReq(s, maxLength);
    }

    @Override
    public void initialize(LastnameValidation constraintAnnotation) {

    }
}
