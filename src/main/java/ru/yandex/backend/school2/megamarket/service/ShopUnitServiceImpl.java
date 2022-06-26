package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitImportDto;
import ru.yandex.backend.school2.megamarket.repository.ShopUnitRepository;

@Service
public class ShopUnitServiceImpl implements ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    @Autowired
    public ShopUnitServiceImpl(ShopUnitRepository shopUnitRepository) {
        this.shopUnitRepository = shopUnitRepository;
    }

    @Override
    public void importShopUnits(ShopUnitImportDto importDto) {

        // validation-level-0:
        // - validate incoming date format on the application level
        // - hibernate validation (standard annotations, custom constraint annotation)

        // validation-level-1:
        // - validate input data for consistence

        // validation-level-2:
        // - map DTO to Entity
        // - read necessary data from DB
        // - validate consistence of the incoming and DB data

        // prepare data for save to DB

    }

}
