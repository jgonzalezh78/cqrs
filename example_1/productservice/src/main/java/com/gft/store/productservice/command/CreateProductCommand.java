package com.gft.store.productservice.command;

import java.math.BigDecimal;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductCommand {
    
    @TargetAggregateIdentifier
    private String productId;   
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
