package com.manuel.store.query.rest;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.store.query.FindProductQuery;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
	
	@Autowired
	QueryGateway queryGateway;
	
	@GetMapping
	public List<ProductQueryModel> getProducts()
	{
		FindProductQuery query = new FindProductQuery();
		
		List<ProductQueryModel> result = queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductQueryModel.class)).join();

		return result;
	}

}
