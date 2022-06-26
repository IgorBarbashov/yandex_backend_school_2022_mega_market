package ru.yandex.backend.school2.megamarket.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumConstraint, Object> {

    private Set<String> allowedValues;

    @Override
    public void initialize(EnumConstraint targetEnum) {
        Class<? extends Enum> enumSelected = targetEnum.targetClassType();
        allowedValues = (Set<String>) EnumSet.allOf(enumSelected).stream().map(e -> ((Enum<? extends Enum<?>>) e).name())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null || allowedValues.contains(((String)value));
    }
}
