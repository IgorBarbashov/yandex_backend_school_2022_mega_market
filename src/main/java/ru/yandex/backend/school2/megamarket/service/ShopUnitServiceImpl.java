package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.repository.ShopUnitRepository;

@Service
public class ShopUnitServiceImpl implements ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    @Autowired
    public ShopUnitServiceImpl(ShopUnitRepository shopUnitRepository) {
        this.shopUnitRepository = shopUnitRepository;
    }

}
