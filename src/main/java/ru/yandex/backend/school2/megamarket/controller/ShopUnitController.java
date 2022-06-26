package ru.yandex.backend.school2.megamarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitImportDto;
import ru.yandex.backend.school2.megamarket.exception.RestApiInvalidDataException;
import ru.yandex.backend.school2.megamarket.service.ShopUnitService;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ShopUnitController {

    private final ShopUnitService shopUnitService;

    @Autowired
    public ShopUnitController(ShopUnitService shopUnitService) {
        this.shopUnitService = shopUnitService;
    }

    @PostMapping(path = "imports")
    @Transactional(rollbackFor = Exception.class)
    public void importShopUnits(@Valid @RequestBody ShopUnitImportDto importDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestApiInvalidDataException();
        }

        shopUnitService.importShopUnits(importDto);
    }

}
