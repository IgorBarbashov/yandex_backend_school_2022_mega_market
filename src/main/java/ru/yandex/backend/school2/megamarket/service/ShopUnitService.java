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
import java.util.List;

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
//                                LocalDateTime updateDate = importDto.getUpdateDate();

        shopUnitValidator.init(shopUnitsDto);

        ArrayList<String> uncheckedRequiredCategoryIds = shopUnitValidator.getUncheckedRequiredCategories();
        int countCategoriesByIds = shopUnitRepository.countCategoriesByIds(uncheckedRequiredCategoryIds);
        if (countCategoriesByIds != uncheckedRequiredCategoryIds.size()) {
            throw new RestApiInvalidDataException();
        }

        // - read necessary data from DB
                        //        List<ShopUnit> shopUnitsDB = shopUnitRepository.findByIdIn(shopUnitIdsDto);
                        //        List<ShopUnit> shopUnitParentsDB = shopUnitRepository.findByIdIn(shopUnitParentIdsDto);

        // - validate consistence of the incoming and DB data

        // prepare data for save to DB
//                                shopUnitMapper.setDateTime(updateDate);
//                                List<ShopUnit> shopUnits = shopUnitMapper.toEntity(shopUnitsDto);

//                                shopUnitRepository.saveAll(shopUnits);

    }

}
