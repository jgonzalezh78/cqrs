package com.manuel.store.core.event;

import lombok.Data;

@Data
public class ProductReservedEvent {
	
	private String productId;
	private String orderId;
	private int quantity;
	private String userId;
}
