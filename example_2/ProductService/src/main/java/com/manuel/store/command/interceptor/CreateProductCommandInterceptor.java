package com.manuel.store.command.interceptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;


import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manuel.store.command.CreateProductCommand;
import com.manuel.store.core.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private final static Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	@Autowired
	private ProductLookupRepository productLookupRepository;
	
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		// TODO Auto-generated method stub
		return (index, command) -> 
			{
				LOGGER.info("Intercepted command type = " + command.getPayloadType());
					
				if (CreateProductCommand.class.equals(command.getPayloadType()))
				{
					CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
					
					if (productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), 
							createProductCommand.getTitle()) != null)
					{
						throw new IllegalStateException(String.format("Product with productId %s or title %s already exists in the database", 
								createProductCommand.getProductId(), createProductCommand.getTitle()));
					}
				}
				return command;
			};
	}

}
