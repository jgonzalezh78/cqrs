package com.gft.store.productservice.core.event;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatedEvent {
    
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;


}
