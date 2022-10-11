package com.gft.store.productservice.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gft.store.productservice.core.data.ProductRepository;
import com.gft.store.productservice.core.data.entity.ProductEntity;
import com.gft.store.productservice.core.event.ProductCreatedEvent;

@Component
public class ProductEventHandler {
    
    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);
        productRepository.save(productEntity);
    }

}
