package com.gft.store.productservice.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.store.productservice.command.CreateProductCommand;
import com.gft.store.productservice.model.CreateProductModel;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@RequestBody CreateProductModel createProductModel){
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                                        .title(createProductModel.getTitle())
                                        .price(createProductModel.getPrice())
                                        .quantity(createProductModel.getQuantity())
                                        .productId(UUID.randomUUID().toString())
                                        .build();

        String result = commandGateway.sendAndWait(createProductCommand);

        return result;

    }
}
