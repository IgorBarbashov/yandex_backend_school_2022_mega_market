package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitImportDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;
import ru.yandex.backend.school2.megamarket.entity.ShopUnitType;
import ru.yandex.backend.school2.megamarket.exception.RestApiInvalidDataException;
import ru.yandex.backend.school2.megamarket.mapper.ShopUnitMapper;
import ru.yandex.backend.school2.megamarket.repository.ShopUnitRepository;
import ru.yandex.backend.school2.megamarket.validation.ShopUnitValidator;

import java.time.LocalDateTime;
import java.util.*;
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
        List<ShopUnit> categoriesByIds = shopUnitRepository.findCategoriesByIds(uncheckedRequiredCategoryIds);
        if (categoriesByIds.size() != uncheckedRequiredCategoryIds.size()) {
            throw new RestApiInvalidDataException();
        }

        HashMap<String, ShopUnitDto> shopUnitsDtoHM = shopUnitValidator.getShopUnits();
        List<ShopUnit> shopUnitsDB = shopUnitRepository.findByIdIn(List.copyOf(shopUnitsDtoHM.keySet()));
        shopUnitValidator.validateImportsAndDB(shopUnitsDB);

        shopUnitMapper.setDateTime(updateDate);
        List<ShopUnit> shopUnits = shopUnitMapper.toEntity(shopUnitsDto);
        setRelations(shopUnits, categoriesByIds);
        shopUnits.addAll(categoriesByIds);
        shopUnitRepository.saveAll(shopUnits);
    }

    public ShopUnit getById(String id) {
        ShopUnit shopUnit = shopUnitRepository.findById(id).get();
        calculatePrice(shopUnit);
        return shopUnit;
    }

    public void delete(String id) {
        shopUnitRepository.deleteById(id);
    }

    public List<ShopUnit> getSales(String date) {
        if (date == null) {
            throw new RestApiInvalidDataException();
        }

        LocalDateTime dateFrom;
        LocalDateTime dateTo;

        try {
            String rawDate = date;
            if (date.indexOf(".") != -1) {
                String[] splittedDateString = date.split("\\.");
                rawDate = splittedDateString[0];
            }
            dateTo = LocalDateTime.parse(rawDate);
            dateFrom = dateTo.minusHours(24);
        } catch (Exception e) {
            throw new RestApiInvalidDataException();
        }

        return shopUnitRepository.getOffersGreaterOrEqualDate(dateFrom.toString(), dateTo.toString());
    }

    private void setRelations(List<ShopUnit> shopUnits, List<ShopUnit> categoriesByIds) {
        List<ShopUnit> combineList = new ArrayList<>(shopUnits);
        combineList.addAll(categoriesByIds);
        HashMap<String, ShopUnit> shopUnitsHM = (HashMap<String, ShopUnit>) combineList
                .stream().collect(Collectors.toMap(ShopUnit::getId, item -> item));

        for (ShopUnit shopUnit : shopUnits) {
            if (shopUnit.getParentId() != null) {
                ShopUnit parent = shopUnitsHM.get(shopUnit.getParentId());
                parent.addChild(shopUnit);
            }
        }
    }

    private long[] calculatePrice(ShopUnit shopUnit) {
        if (shopUnit.getType().equals(ShopUnitType.CATEGORY)) {
            long[] counter = new long[]{0L, 0L};

            if (shopUnit.getChildren().size() > 0) {
                for (ShopUnit child : shopUnit.getChildren()) {
                    long[] childCounter = calculatePrice(child);
                    counter[0] += childCounter[0];
                    counter[1] += childCounter[1];
                }

                if (counter[1] == 0) {
                    shopUnit.setPrice(null);
                } else {
                    double average = Math.floor(counter[0] / counter[1]);
                    shopUnit.setPrice(Double.valueOf(average).longValue());
                }
            } else {
                shopUnit.setPrice(null);
                shopUnit.setChildren(null);
            }
            return counter;
        } else {
            shopUnit.setChildren(null);
            return new long[]{shopUnit.getPrice(), 1L};
        }
    }

}
