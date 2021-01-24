package ro.cpatrut.auth.dto.validation;

import com.google.common.collect.Sets;
import ro.cpatrut.auth.dto.types.GrantType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.commaDelimitedListToSet;

public class SupportedGrantTypesValidator implements ConstraintValidator<SupportedGrantTypes, String> {


    @Override
    public void initialize(final SupportedGrantTypes constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Sets.newHashSet(GrantType.values()).stream()
                .map(GrantType::getType).collect(Collectors.toSet())
                .containsAll(commaDelimitedListToSet(value));
    }
}
