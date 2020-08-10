package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {
    String message() default "Wrong email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
