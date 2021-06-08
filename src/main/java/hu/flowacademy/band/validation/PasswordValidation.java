package hu.flowacademy.band.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<Password, String> {
    private int minUpper;
    private int minDigit;
    private int minLower;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        var filter = value.chars().map(i -> (char) i); // Ha már ugyanarra filterezek rá, ne csináljuk meg 3x. Így elég 1x.

        var upperCaseCount = filter.filter(Character::isUpperCase).count();
        var digitCount = filter.filter(Character::isDigit).count();
        var loweCaseCount = filter.filter(Character::isLowerCase).count();

        // Az adott attribútum alapján beállított annotáció szerint megvizsgálja és TRUE vagy FALSE lesz.
        // Mivel csak a Login-ban a password attribútum kapott ilyet, így 2 upper, 1 lower és 1 digit-re lesz sükség. :)
        return minUpper < upperCaseCount && minDigit < digitCount && minLower < loweCaseCount;
    }

    @Override
    public void initialize(Password constraintAnnotation) {
        this.minUpper = constraintAnnotation.minUpper();
        this.minDigit = constraintAnnotation.minDigit();
        this.minLower = constraintAnnotation.minLower();
    }
}