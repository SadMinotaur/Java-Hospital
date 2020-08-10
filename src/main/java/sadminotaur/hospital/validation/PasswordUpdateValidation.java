package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordUpdateValidation {
    String message() default "Wrong email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
