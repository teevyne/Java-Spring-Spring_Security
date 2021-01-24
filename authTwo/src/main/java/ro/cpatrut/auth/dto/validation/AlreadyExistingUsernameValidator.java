package ro.cpatrut.auth.dto.validation;

import ro.cpatrut.auth.repositories.UserServiceRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlreadyExistingUsernameValidator implements ConstraintValidator<AlreadyExistingUsername, String> {
    private final UserServiceRepository userServiceRepository;

    public AlreadyExistingUsernameValidator(final UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return !userServiceRepository.existsByUsername(username);
    }
}
