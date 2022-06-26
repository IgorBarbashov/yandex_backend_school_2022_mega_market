package ru.yandex.backend.school2.megamarket.validation;

import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;
import ru.yandex.backend.school2.megamarket.entity.ShopUnitType;
import ru.yandex.backend.school2.megamarket.exception.RestApiInvalidDataException;

import java.util.*;

public class ShopUnitValidator {

    private List<ShopUnitDto> shopUnitsDto;

    private HashMap<String, ShopUnitDto> shopUnits;

    private HashMap<String, ShopUnitDto> categories;

    private HashSet<String> requiredCategories;

    private ArrayList<String> uncheckedRequiredCategories;

    public HashMap<String, ShopUnitDto> getShopUnits() {
        return shopUnits;
    }

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

        this.shopUnitsDto.stream().forEach(s -> {
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
    }

    public void validateImportsAndDB(List<ShopUnit> shopUnitsDB) {
        for (ShopUnit shopUnitDB : shopUnitsDB) {
            if (shopUnits.containsKey(shopUnitDB.getId())) {
                String currentType = shopUnitDB.getType().name();
                String newType = shopUnits.get(shopUnitDB.getId()).getType();
                if (!newType.equals(currentType)) {
                    throw new RestApiInvalidDataException();
                }
            }
        }
    }

}
