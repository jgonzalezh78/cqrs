package com.manuel.store.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manuel.store.core.data.ProductEntity;
import com.manuel.store.core.data.ProductRepository;
import com.manuel.store.core.event.ProductCreatedEvent;
import com.manuel.store.core.event.ProductReservationCancelledEvent;
import com.manuel.store.core.event.ProductReservedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
	
	private ProductRepository productRepository;
	
	public ProductEventsHandler(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent)
	{
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productCreatedEvent, productEntity);
		productRepository.save(productEntity);
	}

	@EventHandler
	public void on(ProductReservedEvent productReservedEvent)
	{
		ProductEntity productEntity = productRepository.findProductsByProductId(productReservedEvent.getProductId()).get(0);
	    productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
		productRepository.save(productEntity);
	}
	
	@EventHandler
	public void on(ProductReservationCancelledEvent productReservationCancelledEvent)
	{
		ProductEntity productEntity = productRepository.findProductsByProductId(productReservationCancelledEvent.getProductId()).get(0);
	    productEntity.setQuantity(productEntity.getQuantity() + productReservationCancelledEvent.getQuantity());
		productRepository.save(productEntity);
	}

}
