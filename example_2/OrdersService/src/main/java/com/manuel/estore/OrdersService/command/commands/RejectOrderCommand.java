package com.manuel.estore.OrdersService.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectOrderCommand {

	@TargetAggregateIdentifier
    private String orderId;
	private String reason;
}
