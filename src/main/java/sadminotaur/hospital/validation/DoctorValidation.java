package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DoctorValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoctorValidation {
    String message() default "Wrong schedule";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
