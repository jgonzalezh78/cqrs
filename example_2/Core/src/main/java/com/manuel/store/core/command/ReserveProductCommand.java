package com.manuel.store.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveProductCommand {
	
	@TargetAggregateIdentifier
	private String productId;
	private String orderId;
	private int quantity;
	private String userId;

}
