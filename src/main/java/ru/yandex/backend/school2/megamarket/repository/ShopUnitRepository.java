package ru.yandex.backend.school2.megamarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;

@Repository
public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {
}
