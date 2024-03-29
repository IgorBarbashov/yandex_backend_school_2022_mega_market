package ru.yandex.backend.school2.megamarket.dto;

import ru.yandex.backend.school2.megamarket.entity.ShopUnitType;
import ru.yandex.backend.school2.megamarket.validation.constraint.EnumConstraint;
import ru.yandex.backend.school2.megamarket.validation.constraint.PriceAndTypeMatchConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@PriceAndTypeMatchConstraint()
public class ShopUnitDto {

    // TODO - valid uuid format
    @NotNull
    private String id;

    @NotNull
    private String name;

    // TODO - valid uuid format
    private String parentId;

    @NotNull
    @EnumConstraint(targetClassType = ShopUnitType.class)
    private String type;

    @Digits(integer = 19, fraction = 0)
    private Double price;

    public ShopUnitDto() {
    }

    public ShopUnitDto(String id, String name, String parentId, String type, Double price) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopUnitDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

}
