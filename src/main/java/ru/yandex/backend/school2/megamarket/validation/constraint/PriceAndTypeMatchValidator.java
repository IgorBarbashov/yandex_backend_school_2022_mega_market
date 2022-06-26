package ru.yandex.backend.school2.megamarket.validation.constraint;

import org.springframework.beans.BeanWrapperImpl;
import ru.yandex.backend.school2.megamarket.entity.ShopUnitType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceAndTypeMatchValidator implements ConstraintValidator<PriceAndTypeMatchConstraint, Object> {

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Double priceValue = (Double) new BeanWrapperImpl(value).getPropertyValue("price");
        String typeValue = (String) new BeanWrapperImpl(value).getPropertyValue("type");

        if (typeValue == null) {
            return false;
        }

        if (typeValue.equals(ShopUnitType.CATEGORY.name())) {
            return priceValue == null;
        } else if (typeValue.equals(ShopUnitType.OFFER.name())) {
            return priceValue != null && priceValue >= 0 && priceValue <= Long.MAX_VALUE;
        }

        return false;
    }
}
