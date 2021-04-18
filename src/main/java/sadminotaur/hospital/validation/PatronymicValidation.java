package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PatronymicValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatronymicValidation {

    String message() default "Wrong patronymic";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
