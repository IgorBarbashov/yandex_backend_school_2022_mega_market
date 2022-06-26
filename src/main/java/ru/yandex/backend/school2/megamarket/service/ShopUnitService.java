package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitImportDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;
import ru.yandex.backend.school2.megamarket.exception.RestApiInvalidDataException;
import ru.yandex.backend.school2.megamarket.mapper.ShopUnitMapper;
import ru.yandex.backend.school2.megamarket.repository.ShopUnitRepository;
import ru.yandex.backend.school2.megamarket.validation.ShopUnitValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    private final ShopUnitMapper shopUnitMapper;

    private final ShopUnitValidator shopUnitValidator;

    @Autowired
    public ShopUnitService(ShopUnitRepository shopUnitRepository, ShopUnitMapper shopUnitMapper, ShopUnitValidator shopUnitValidator) {
        this.shopUnitRepository = shopUnitRepository;
        this.shopUnitMapper = shopUnitMapper;
        this.shopUnitValidator = shopUnitValidator;
    }

    public void importShopUnits(ShopUnitImportDto importDto) {
        List<ShopUnitDto> shopUnitsDto = importDto.getItems();
        LocalDateTime updateDate = importDto.getUpdateDate();

        shopUnitValidator.init(shopUnitsDto);

        ArrayList<String> uncheckedRequiredCategoryIds = shopUnitValidator.getUncheckedRequiredCategories();
        int countCategoriesByIds = shopUnitRepository.countCategoriesByIds(uncheckedRequiredCategoryIds);
        if (countCategoriesByIds != uncheckedRequiredCategoryIds.size()) {
            throw new RestApiInvalidDataException();
        }

        HashMap<String, ShopUnitDto> shopUnitsDtoHM = shopUnitValidator.getShopUnits();
        List<ShopUnit> shopUnitsDB = shopUnitRepository.findByIdIn(List.copyOf(shopUnitsDtoHM.keySet()));
        shopUnitValidator.validateImportsAndDB(shopUnitsDB);

        shopUnitMapper.setDateTime(updateDate);
        List<ShopUnit> shopUnits = shopUnitMapper.toEntity(shopUnitsDto);
        setRelations(shopUnits);

        shopUnitRepository.saveAll(shopUnits);
    }

    private void setRelations(List<ShopUnit> shopUnits) {
        HashMap<String, ShopUnit> shopUnitsHM = (HashMap<String, ShopUnit>) shopUnits
                .stream().collect(Collectors.toMap(ShopUnit::getId, item -> item));

        for (ShopUnit shopUnit : shopUnits) {
            if (shopUnit.getParentId() != null) {
                ShopUnit parent = shopUnitsHM.get(shopUnit.getParentId());
                parent.addChild(shopUnit);
            }
        }
    }

}
