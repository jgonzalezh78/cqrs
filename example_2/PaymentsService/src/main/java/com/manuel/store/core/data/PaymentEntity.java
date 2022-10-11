package com.manuel.store.core.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
	
	@Id
	private String paymentId;
	private String orderId;

}
