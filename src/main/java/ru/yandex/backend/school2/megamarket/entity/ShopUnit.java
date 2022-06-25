package ru.yandex.backend.school2.megamarket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity
@Table(name = "shop_unit")
public class ShopUnit {

    @Id
    @Column(name = "id")
    // TODO - valid uuid format
    // TODO - not null
    private String id;

    @Column(name = "name")
    // TODO - not null
    private String name;

    @Column(name = "date")
    // TODO - not null
    // TODO - iso
    @JsonFormat(shape = STRING, timezone = "UTC")
    private LocalDateTime date;

    @Column(name = "parent_id")
    // TODO - valid uuid format
    private String parentId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    // TODO - enum ShopUnitType
    private ShopUnitType type;

    @Column(name = "price")
    // TODO - constraint according `type`
    // TODO - validate size and type
    private Long price;

    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ShopUnit> children = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "parent")
    @JsonIgnore
    private ShopUnit parent;

    public ShopUnit() {
    }

    public ShopUnit(String id, String name, LocalDateTime date, String parentId, ShopUnitType type, Long price) {
        this.id = id;
        this.name = name;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(List<ShopUnit> children) {
        this.children = children;
    }

    public ShopUnit getParent() {
        return parent;
    }

    public void setParent(ShopUnit parent) {
        this.parent = parent;
    }

    public void addChild(ShopUnit shopUnit) {
        children.add(shopUnit);
        shopUnit.setParent(this);
    }

    public void removeChild(ShopUnit shopUnit) {
        children.remove(shopUnit);
        shopUnit.setParent(null);
    }

    public void changeParent(ShopUnit newParent) {
        this.parent.getChildren().remove(this);
        this.setParent(newParent);
        newParent.getChildren().add(this);
    }

    @Override
    public String toString() {
        return "\nShopUnit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId='" + parentId + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopUnit)) return false;
        return id != null && id.equals(((ShopUnit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
