package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FirstnameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstnameValidation {
    String message() default "Wrong firstname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
