package hu.flowacademy.band.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    int minUpper() default 1;
    int minDigit() default 1;
    int minLower() default 1;
}
