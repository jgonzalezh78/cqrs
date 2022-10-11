package com.manuel.store.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateProductCommand {
	
	@TargetAggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
