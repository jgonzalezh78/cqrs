package com.manuel.store.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.manuel.store.core.command.CancelProductReservationCommand;
import com.manuel.store.core.command.ReserveProductCommand;
import com.manuel.store.core.event.ProductCreatedEvent;
import com.manuel.store.core.event.ProductReservationCancelledEvent;
import com.manuel.store.core.event.ProductReservedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate()
	{
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand)
	{
		if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new IllegalArgumentException("Price should be bigger than 0");
		}
		
		if (createProductCommand.getTitle() == null || 
				createProductCommand.getTitle().isBlank() )
		{
			throw new IllegalArgumentException("Title should not be empty");
		}
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		
		AggregateLifecycle.apply(productCreatedEvent);	
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand)
	{	
		if (reserveProductCommand.getQuantity() > this.quantity)
		{
			throw new IllegalStateException("Not enough quantity for requested reservation");
		}
		
		ProductReservedEvent productReservedEvent = new ProductReservedEvent();
		BeanUtils.copyProperties(reserveProductCommand, productReservedEvent);
		
		AggregateLifecycle.apply(productReservedEvent);	
	}
	
	@CommandHandler
	public void handle(CancelProductReservationCommand cancelProductReservationCommand)
	{	
		ProductReservationCancelledEvent productReservationCancelledEvent = ProductReservationCancelledEvent.builder()
				.orderId(cancelProductReservationCommand.getOrderId())
				.productId(cancelProductReservationCommand.getProductId())
				.quantity(cancelProductReservationCommand.getQuantity())
				.userId(cancelProductReservationCommand.getUserId())
				.reason(cancelProductReservationCommand.getReason()).build();
		AggregateLifecycle.apply(productReservationCancelledEvent);	
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent)
	{
		this.productId = productCreatedEvent.getProductId();
		this.title = productCreatedEvent.getTitle();
		this.price = productCreatedEvent.getPrice();
		this.quantity = productCreatedEvent.getQuantity();
	}

	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent)
	{
		this.quantity = this.quantity - productReservedEvent.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(ProductReservationCancelledEvent productReservationCancelledEvent)
	{
		this.quantity += productReservationCancelledEvent.getQuantity();
	}
	
	
}
