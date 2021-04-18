package sadminotaur.hospital.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordUpdateValidator implements ConstraintValidator<PasswordUpdateValidation, Object> {

    @Value("${min_password_length}")
    private int minLength;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String oldPassword = (String) new BeanWrapperImpl(o).getPropertyValue("oldPassword");
        String newPassword = (String) new BeanWrapperImpl(o).getPropertyValue("newPassword");
        if (oldPassword != null && newPassword != null) {
            return !oldPassword.equals(newPassword) &&
                    newPassword.length() >= minLength &&
                    oldPassword.length() >= minLength;
        }
        return false;
    }
}
