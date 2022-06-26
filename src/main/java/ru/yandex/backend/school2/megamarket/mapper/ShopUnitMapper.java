package ru.yandex.backend.school2.megamarket.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import ru.yandex.backend.school2.megamarket.dto.ShopUnitDto;
import ru.yandex.backend.school2.megamarket.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShopUnitMapper implements EntityDtoMapper<ShopUnit, ShopUnitDto> {

    private ModelMapper modelMapper;

    private LocalDateTime dateTime;

    public ShopUnitMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        this.modelMapper = mapper;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ShopUnit toEntity(ShopUnitDto source) {
        return toEntity(source, ShopUnit.class);
    }

    @Override
    public ShopUnit toEntity(ShopUnitDto source, Class<ShopUnit> destinationType) {
        ShopUnit shopUnit = modelMapper.map(source, destinationType);
        shopUnit.setParent(null);
        shopUnit.setDate(dateTime);
        return shopUnit;
    }

    public List<ShopUnit> toEntity(List<ShopUnitDto> source) {
        return toEntity(source, ShopUnit.class);
    }

    @Override
    public List<ShopUnit> toEntity(List<ShopUnitDto> source, Class<ShopUnit> destinationType) {
        return source
                .stream()
                .map(s -> toEntity(s, destinationType))
                .collect(Collectors.toList());
    }

    public ShopUnitDto toDto(ShopUnit source) {
        return modelMapper.map(source, ShopUnitDto.class);
    }

    @Override
    public ShopUnitDto toDto(ShopUnit source, Class<ShopUnitDto> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public List<ShopUnitDto> toDto(List<ShopUnit> source, Class<ShopUnitDto> destinationType) {
        return source
                .stream()
                .map(s -> modelMapper.map(s, destinationType))
                .collect(Collectors.toList());
    }
}
