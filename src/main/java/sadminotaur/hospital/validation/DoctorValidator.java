package sadminotaur.hospital.validation;

import sadminotaur.hospital.requests.DaySchedule;
import sadminotaur.hospital.requests.WeekSchedule;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoctorValidator implements ConstraintValidator<DoctorValidation, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        DaySchedule[] weekDaysSchedule = (DaySchedule[]) new BeanWrapperImpl(o).getPropertyValue("weekDaysSchedule");
        WeekSchedule weekSchedule = (WeekSchedule) new BeanWrapperImpl(o).getPropertyValue("weekSchedule");
        return weekDaysSchedule != null || weekSchedule != null;
    }
}
