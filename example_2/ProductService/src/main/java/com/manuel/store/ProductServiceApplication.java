package com.manuel.store;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.manuel.store.command.interceptor.CreateProductCommandInterceptor;
import com.manuel.store.core.errorhandler.ProductsServiceEventsErrorHandler;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, 
			CommandBus commandBus)
	{
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config)
	{
	     config.registerListenerInvocationErrorHandler("product-group", conf -> new ProductsServiceEventsErrorHandler());
	}

}
