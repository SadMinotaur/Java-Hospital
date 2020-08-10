package sadminotaur.hospital.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TicketRegistrationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TicketValidation {
    String message() default "Wrong request parameters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

