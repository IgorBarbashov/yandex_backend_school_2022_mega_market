package ru.yandex.backend.school2.megamarket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "good")
public class Good {

    @Id
    @Column(name = "id")
    private String id;

}
