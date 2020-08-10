package sadminotaur.hospital.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone != null) {
            return (phone.startsWith("8") || phone.startsWith("+7")) &&
                    phone.length() < 17 &&
                    phone.matches("[1-9]{11}") &&
                    !phone.matches("\\s");
        }
        return false;
    }
}
