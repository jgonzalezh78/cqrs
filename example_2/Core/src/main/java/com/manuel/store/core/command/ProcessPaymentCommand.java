package com.manuel.store.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.manuel.store.core.model.PaymentDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessPaymentCommand {
	
	@TargetAggregateIdentifier
	private String paymentId;
	private String orderId;
	private PaymentDetails paymentDetails;

}
