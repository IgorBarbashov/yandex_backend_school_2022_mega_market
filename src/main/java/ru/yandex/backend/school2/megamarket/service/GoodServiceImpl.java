package ru.yandex.backend.school2.megamarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.backend.school2.megamarket.repository.GoodRepository;

@Service
public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;

    @Autowired
    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

}
