package ro.cpatrut.auth.dto.validation;

import com.google.common.collect.Sets;
import ro.cpatrut.auth.dto.types.Authorities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.commaDelimitedListToSet;

public class AuthoritiesValidator implements ConstraintValidator<SupportedAuthorities, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Sets.newHashSet(Authorities.values()).stream()
                .map(Enum::toString).collect(Collectors.toSet())
                .containsAll(commaDelimitedListToSet(value));


    }
}
