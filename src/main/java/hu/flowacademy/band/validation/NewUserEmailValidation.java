package hu.flowacademy.band.validation;

import hu.flowacademy.band.database.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewUserEmailValidation implements ConstraintValidator<Password, String> {

    private final UserRepository userRepository;

    public NewUserEmailValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !userRepository.existsByEmail(value);
    }
}