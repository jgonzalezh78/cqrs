package com.manuel.store.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.store.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
	
	private CommandGateway commandGateway;
	
	public ProductCommandController(CommandGateway commandGateway)
	{
		this.commandGateway = commandGateway;
	}
	
	@PostMapping
	public String createProduct(@RequestBody CreateProductCommandModel createProductCommandModel)
	{
		CreateProductCommand createProductCommand = new CreateProductCommand();
		BeanUtils.copyProperties(createProductCommandModel, createProductCommand);
		createProductCommand.setProductId(UUID.randomUUID().toString());
		String result = null;
		
		result = commandGateway.sendAndWait(createProductCommand);
		
//		try
//		{
//			result = commandGateway.sendAndWait(createProductCommand);
//		}
//		catch(Exception e)
//		{
//			result = e.getLocalizedMessage();
//		}
		return result;
	}

}
