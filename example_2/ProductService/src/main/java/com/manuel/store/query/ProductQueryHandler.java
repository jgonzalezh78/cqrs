package com.manuel.store.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manuel.store.core.data.ProductEntity;
import com.manuel.store.core.data.ProductRepository;
import com.manuel.store.query.rest.ProductQueryModel;

@Component
public class ProductQueryHandler {

	@Autowired
	ProductRepository productRepository;
	
	@QueryHandler
	public List<ProductQueryModel> findProducts(FindProductQuery findProductQuery)
	{
		List<ProductQueryModel> result = new ArrayList<>();
		List<ProductEntity> dbProducts = productRepository.findAll();
		
		dbProducts.forEach(p -> 
		{
			ProductQueryModel product = new ProductQueryModel();
			BeanUtils.copyProperties(p, product);
			result.add(product);
		});
		
	    return result;
	}
	
}
