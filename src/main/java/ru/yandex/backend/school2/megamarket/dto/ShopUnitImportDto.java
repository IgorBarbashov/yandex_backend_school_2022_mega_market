package ru.yandex.backend.school2.megamarket.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ShopUnitImportDto {

    @Valid
    @NotEmpty
    private List<ShopUnitDto> items;

    @NotNull
    @DateTimeFormat
    private LocalDateTime updateDate;

    public ShopUnitImportDto() {
    }

    public ShopUnitImportDto(List<ShopUnitDto> items, LocalDateTime updateDate) {
        this.items = items;
        this.updateDate = updateDate;
    }

    public List<ShopUnitDto> getItems() {
        return items;
    }

    public void setItems(List<ShopUnitDto> items) {
        this.items = items;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "ShopUnitImportDto{" +
                "items=" + items +
                ", updateDate=" + updateDate +
                '}';
    }

}
