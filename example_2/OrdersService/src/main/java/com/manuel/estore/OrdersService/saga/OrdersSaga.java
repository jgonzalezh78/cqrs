package com.manuel.estore.OrdersService.saga;

import java.util.UUID;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manuel.estore.OrdersService.command.commands.ApproveOrderCommand;
import com.manuel.estore.OrdersService.core.events.OrderApprovedEvent;
import com.manuel.estore.OrdersService.core.events.OrderCreatedEvent;
import com.manuel.store.core.command.ProcessPaymentCommand;
import com.manuel.store.core.command.ReserveProductCommand;
import com.manuel.store.core.event.PaymentProcessedEvent;
import com.manuel.store.core.event.ProductReservedEvent;
import com.manuel.store.core.model.User;
import com.manuel.store.core.query.FetchUserPaymentDetailsQuery;

@Saga
public class OrdersSaga {
 
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway querydGateway; 
    

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersSaga.class);

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        LOGGER.info("Saga started");
        LOGGER.info("OrderCreated handled for order "+ orderCreatedEvent.getOrderId());
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                                                        .orderId(orderCreatedEvent.getOrderId())
                                                        .productId(orderCreatedEvent.getProductId())
                                                        .userId(orderCreatedEvent.getUserId())
                                                        .quantity(orderCreatedEvent.getQuantity()).build();
        
                                                        commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>(){
                                                            @Override
                                                            public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage, 
                                                            CommandResultMessage<? extends Object> commandResultMessage){
                                                                if(commandResultMessage.isExceptional()){
                                                                    //tenemos que hacer algo
                                                                }
                                                            }
                                                        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent){
        LOGGER.info("ProductReservedEvent handled for order "+ productReservedEvent.getOrderId());

        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = 
                            new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());
        User userPaymentsDetails = null;

        try{
            userPaymentsDetails = querydGateway.query(fetchUserPaymentDetailsQuery, User.class).join();
        }catch(Exception e){
            LOGGER.error("Error obtaining user payment details" + e.getMessage());
            return;
        }
        if(userPaymentsDetails == null){
            LOGGER.error("Error obtaining user payment details");
            return;
        }

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                                                        .orderId(productReservedEvent.getOrderId())
                                                        .paymentId(UUID.randomUUID().toString())
                                                        .paymentDetails(userPaymentsDetails.getPaymentDetails())
                                                        .build();

            String result = null;
            try{
                result = commandGateway.sendAndWait(processPaymentCommand);
            }catch(Exception e){
                LOGGER.error("Error executing payment command "+ e.getMessage());
            }
    }

    @SagaEventHandler (associationProperty = "orderId")
    public void handler(PaymentProcessedEvent paymentProcessedEvent){
        LOGGER.info("Handled PaymentProcessedEvent for order :" + paymentProcessedEvent.getOrderId());
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());
        String result = null;

        try{
            result = commandGateway.sendAndWait(approveOrderCommand);
        }catch(Exception e){
            LOGGER.error("Error approving order "+ e.getMessage());
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent ){
        LOGGER.info("Handled OrderApprovedEvent for order :" + orderApprovedEvent.getOrderId());
        LOGGER.info("Saga finished");
        
    }

}

