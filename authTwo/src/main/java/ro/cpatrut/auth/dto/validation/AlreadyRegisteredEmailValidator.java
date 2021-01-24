package ro.cpatrut.auth.dto.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ro.cpatrut.auth.repositories.UserServiceRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlreadyRegisteredEmailValidator implements ConstraintValidator<AlreadyRegisteredEmail, String> {
    private final UserServiceRepository userServiceRepository;

    @Autowired
    public AlreadyRegisteredEmailValidator(final UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    @Override
    public boolean isValid(final String emailAddress, final ConstraintValidatorContext context) {
        return !userServiceRepository.existsByEmailAddress(emailAddress);
    }
}
