package hu.flowacademy.band.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://www.baeldung.com/java-string-valid-date
 */
@Constraint(validatedBy = DateValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
    String message() default "Hibás dátum formátum. Elvárt: yyyy-mm-dd";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
