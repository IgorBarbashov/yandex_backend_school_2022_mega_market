package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitImportDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;
import ru.yandex.backend.school2.megamarket.mapper.ShopUnitMapper;
import ru.yandex.backend.school2.megamarket.repository.ShopUnitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopUnitServiceImpl implements ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    private final ShopUnitMapper shopUnitMapper;

    @Autowired
    public ShopUnitServiceImpl(ShopUnitRepository shopUnitRepository, ShopUnitMapper shopUnitMapper) {
        this.shopUnitRepository = shopUnitRepository;
        this.shopUnitMapper = shopUnitMapper;
    }

    @Override
    public void importShopUnits(ShopUnitImportDto importDto) {

        List<ShopUnitDto> shopUnitsDto = importDto.getItems();
        LocalDateTime updateDate = importDto.getUpdateDate();

        List<String> shopUnitIdsDto = shopUnitsDto.stream().map(ShopUnitDto::getId).collect(Collectors.toList());
        List<String> shopUnitParentIdsDto = shopUnitsDto.stream().map(ShopUnitDto::getParentId).collect(Collectors.toList());

        // validation-level-0:
        // - validate incoming date format on the application level
        // - hibernate validation (standard annotations, custom constraint annotation)

        // validation-level-1:
        // - validate input data for consistence

        // validation-level-2:
        // - map DTO to Entity
        shopUnitMapper.setDateTime(updateDate);
        List<ShopUnit> shopUnits = shopUnitMapper.toEntity(shopUnitsDto);

        // - read necessary data from DB
        List<ShopUnit> shopUnitsDB = shopUnitRepository.findByIdIn(shopUnitIdsDto);
        List<ShopUnit> shopUnitParentsDB = shopUnitRepository.findByIdIn(shopUnitParentIdsDto);

        // - validate consistence of the incoming and DB data

        // prepare data for save to DB

        shopUnitRepository.saveAll(shopUnits);

    }

}
