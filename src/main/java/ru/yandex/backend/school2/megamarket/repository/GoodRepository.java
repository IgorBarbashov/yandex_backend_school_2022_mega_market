package ru.yandex.backend.school2.megamarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.backend.school2.megamarket.entity.Good;

@Repository
public interface GoodRepository extends JpaRepository<Good, String> {
}
