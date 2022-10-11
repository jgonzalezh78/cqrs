package com.manuel.store.query.rest;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductQueryModel {
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
