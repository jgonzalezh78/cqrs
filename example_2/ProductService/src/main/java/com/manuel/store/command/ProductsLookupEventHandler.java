package com.manuel.store.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.manuel.store.core.data.ProductLookupEntity;
import com.manuel.store.core.data.ProductLookupRepository;
import com.manuel.store.core.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductsLookupEventHandler {

	private final ProductLookupRepository productLookupRepository;
	
	public ProductsLookupEventHandler(ProductLookupRepository productLookupRepository)
	{
		this.productLookupRepository=productLookupRepository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent)
	{
		ProductLookupEntity productLookupEntity = new ProductLookupEntity(productCreatedEvent
				.getProductId(), productCreatedEvent.getTitle());
		productLookupRepository.save(productLookupEntity);
		
	}
}
