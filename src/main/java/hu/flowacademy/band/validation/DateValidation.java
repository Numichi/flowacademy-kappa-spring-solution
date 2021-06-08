package hu.flowacademy.band.validation;

import hu.flowacademy.band.ConstantValues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * https://www.baeldung.com/java-string-valid-date
 */
public class DateValidation implements ConstraintValidator<Date, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Most itt false! :) Ezért nincs @NotNull
        }

        try {
            // Ha nem alakítható át, akkor megdöglik. Ami jó! :D Tehát cache.
            LocalDate.parse(value, ConstantValues.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}