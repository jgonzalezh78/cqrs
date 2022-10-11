package com.gft.store.productservice.core.data;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.gft.store.productservice.command.CreateProductCommand;
import com.gft.store.productservice.core.event.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
    
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        ProductCreatedEvent productCreatedEvent = ProductCreatedEvent.builder()
                                                    .productId(createProductCommand.getProductId())
                                                    .title(createProductCommand.getTitle())
                                                    .price(createProductCommand.getPrice())
                                                    .quantity(createProductCommand.getQuantity())
                                                    .build();    
    
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void onProductCreateEvent(ProductCreatedEvent productCreatedEvent){
        productId = productCreatedEvent.getProductId();
        title = productCreatedEvent.getTitle();
        quantity = productCreatedEvent.getQuantity();
        price = productCreatedEvent.getPrice();
    }

}
