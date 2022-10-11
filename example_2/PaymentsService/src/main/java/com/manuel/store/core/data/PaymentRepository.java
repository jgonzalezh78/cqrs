package com.manuel.store.core.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

	public List<PaymentEntity> findPaymentsByPaymentId(String paymentId);
}
