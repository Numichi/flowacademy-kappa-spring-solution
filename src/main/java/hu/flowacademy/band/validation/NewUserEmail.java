package hu.flowacademy.band.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NewUserEmailValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewUserEmail {
    String message() default "Email cím már foglalt!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
