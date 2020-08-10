package sadminotaur.hospital.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TicketRegistrationValidator implements ConstraintValidator<TicketValidation, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String speciality = (String) new BeanWrapperImpl(o).getPropertyValue("speciality");
        int doctorId = 0;
        try {
            doctorId = (int) new BeanWrapperImpl(o).getPropertyValue("doctorId");
        } catch (NullPointerException e) {
            return speciality != null;
        }
        return (doctorId != 0 && speciality == null) ||
                (doctorId == 0 && speciality != null);
    }

}
