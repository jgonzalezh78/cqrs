package com.gft.store.productservice.core.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="products")
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String productId;
    @Column
    private String title;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;

}
