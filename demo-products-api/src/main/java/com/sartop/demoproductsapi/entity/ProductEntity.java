package com.sartop.demoproductsapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProduct status;
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private ClientEntity client;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "cateogry", nullable = false)
    private ProductCategory category;


    @Column(name = "forSaleAt")
    private LocalDateTime forSaleAt;
    @Column(name = "soldAt")
    private LocalDateTime soldAt;
    @CreatedDate
    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @LastModifiedDate
    @Column(name = "modificationDate")
    private LocalDateTime modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @CreatedBy
    @Column(name = "createBy")
    private String createBy;
    @LastModifiedBy
    @Column(name = "modifiedBy")
    private String modifiedBy;

    public enum StatusProduct
    {
        ON_STOCK,
        FOR_SALE, SOLD
    }

    public enum ProductCategory
    {
        DAIRY, PRODUCE, FREEZER, SEAFOOD, SNAKCS, BEVERAGE, BAKERY, CONDIMENTS, DELI, MEAT, BAKING, CARE_PRODUCTS,
        DRIED_GOODS, VEGETABLES, CANNED_FOOD, BREAD, CEREAL, FRUITS, GRAINS, INTERNATIONAL_FOOD, EGGS_AND_DAIRY, FAT_AND_OIL, PROTEIN_FOODS,
        CLEANING, BABY_ITENS, CLOUTHING, TECHNOLOGIE
    }
}
