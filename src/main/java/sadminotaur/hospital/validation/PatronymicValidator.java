package sadminotaur.hospital.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatronymicValidator implements ConstraintValidator<PatronymicValidation, String> {

    @Value("${max_name_length}")
    private int maxLength;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return UserValidUtils.patronymicReq(value, maxLength);
    }
}
