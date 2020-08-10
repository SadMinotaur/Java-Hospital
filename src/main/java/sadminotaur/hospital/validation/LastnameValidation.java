package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LastnameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastnameValidation {
    String message() default "Wrong lastname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
