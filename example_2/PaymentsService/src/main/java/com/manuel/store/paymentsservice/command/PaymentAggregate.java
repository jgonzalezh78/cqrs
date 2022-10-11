package com.manuel.store.paymentsservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.manuel.store.core.command.ProcessPaymentCommand;
import com.manuel.store.core.event.PaymentProcessedEvent;

import lombok.Data;

@Aggregate
@Data
public class PaymentAggregate {
	
	@AggregateIdentifier
	private String paymentId;
	private String orderId;
	
	public PaymentAggregate()
	{
	}
	
	@CommandHandler
	public PaymentAggregate(ProcessPaymentCommand processPaymentCommand)
	{
		if (processPaymentCommand.getOrderId() == null)
		{
			throw new IllegalArgumentException("OrderId should not be null");
		}
		if (processPaymentCommand.getPaymentId() == null)
		{
			throw new IllegalArgumentException("PaymentId should not be null");
		}
		PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
				.orderId(processPaymentCommand.getOrderId())
				.paymentId(processPaymentCommand.getPaymentId()).build();
		AggregateLifecycle.apply(paymentProcessedEvent);
	}
	
	@EventSourcingHandler
	public void on(PaymentProcessedEvent paymentProcessedEvent)
	{
		this.orderId = paymentProcessedEvent.getOrderId();
		this.paymentId = paymentProcessedEvent.getPaymentId();
	}

}
