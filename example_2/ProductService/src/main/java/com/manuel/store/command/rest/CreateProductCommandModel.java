package com.manuel.store.command.rest;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateProductCommandModel {
	
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
