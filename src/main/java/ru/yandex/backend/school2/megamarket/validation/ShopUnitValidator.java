package ru.yandex.backend.school2.megamarket.validation;

import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnitType;
import ru.yandex.backend.school2.megamarket.exception.RestApiInvalidDataException;

import java.util.*;

public class ShopUnitValidator {

    private List<ShopUnitDto> shopUnitsDto;

    private HashMap<String, ShopUnitDto> shopUnits; // все входящие сущности

    private HashMap<String, ShopUnitDto> categories; // входящие сущности, которые сами являются категориями

    private HashSet<String> requiredCategories; // список id сущностей, на которых ссылаются как на категории (может быть БД)

    private ArrayList<String> uncheckedRequiredCategories; // shopUnitRequiredCategories, которых нет в imports, их надо проверять по БД

    public ArrayList<String> getUncheckedRequiredCategories() {
        return uncheckedRequiredCategories;
    }

    public ShopUnitValidator() {
    }

    public void init(List<ShopUnitDto> shopUnitsDto) {
        this.shopUnitsDto = shopUnitsDto;
        this.shopUnits = new HashMap<>();
        this.categories = new HashMap<>();
        this.requiredCategories = new HashSet<>();
        this.uncheckedRequiredCategories = new ArrayList<>();

        shopUnitsDto.stream().forEach(s -> {
            if (shopUnits.containsKey(s.getId())) {
                throw new RestApiInvalidDataException();
            }

            if (s.getType().equals(ShopUnitType.CATEGORY.name())) {
                categories.put(s.getId(), s);
            }

            if (s.getParentId() != null) {
                requiredCategories.add(s.getParentId());
            }

            shopUnits.put(s.getId(), s);
        });

        validateImports();
    }

    private void validateImports() {
        requiredCategories.stream().forEach(rc -> {
            if (shopUnits.containsKey(rc)) {
                if (!categories.containsKey(rc)) {
                    throw new RestApiInvalidDataException();
                }
            } else {
                uncheckedRequiredCategories.add(rc);
            }
        });

        System.out.println(uncheckedRequiredCategories);
    }

}
