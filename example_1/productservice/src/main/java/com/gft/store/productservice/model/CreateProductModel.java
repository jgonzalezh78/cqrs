package com.gft.store.productservice.model;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class CreateProductModel {

   
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
