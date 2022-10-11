package com.manuel.estore.OrdersService.core.events;

import com.manuel.estore.OrdersService.core.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderApprovedEvent {

	private String orderId;
	private OrderStatus orderStatus;
}
