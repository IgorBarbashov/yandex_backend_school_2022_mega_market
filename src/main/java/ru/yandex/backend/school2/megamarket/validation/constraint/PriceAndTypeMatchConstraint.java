package ru.yandex.backend.school2.megamarket.validation.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceAndTypeMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceAndTypeMatchConstraint {

    String message() default "Price doesn\'t match to Type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
