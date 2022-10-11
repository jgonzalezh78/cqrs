package com.manuel.store.core.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manuel.store.core.data.PaymentEntity;
import com.manuel.store.core.data.PaymentRepository;

@Component
public class PaymentEventsHandler {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@EventHandler
	public void handle(PaymentProcessedEvent paymentProcessedEvent)
	{
		PaymentEntity paymentEntity = PaymentEntity.builder()
				.orderId(paymentProcessedEvent.getOrderId())
				.paymentId(paymentProcessedEvent.getPaymentId()).build();
		paymentRepository.save(paymentEntity);
	}
}
