/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuel.estore.OrdersService.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.manuel.estore.OrdersService.command.commands.ApproveOrderCommand;
import com.manuel.estore.OrdersService.command.commands.CreateOrderCommand;
import com.manuel.estore.OrdersService.command.commands.RejectOrderCommand;
import com.manuel.estore.OrdersService.core.events.OrderApprovedEvent;
import com.manuel.estore.OrdersService.core.events.OrderCreatedEvent;
import com.manuel.estore.OrdersService.core.events.OrderRejectedEvent;
import com.manuel.estore.OrdersService.core.model.OrderStatus;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderAggregate.class);
    
    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {   
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        
        AggregateLifecycle.apply(orderCreatedEvent);
    }
    
    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand)
    {
    	LOGGER.info("Entering CommandHandler for ApproveOrderCommand for orderId : " + approveOrderCommand.getOrderId());
    	OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId(), 
    			OrderStatus.APPROVED);
    	AggregateLifecycle.apply(orderApprovedEvent);
    }
    
    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand)
    {
    	LOGGER.info("Entering CommandHandler for RejectOrderCommand for orderId : " + rejectOrderCommand.getOrderId());
    	OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent(rejectOrderCommand.getOrderId(), 
    			OrderStatus.REJECTED, rejectOrderCommand.getReason());
    	AggregateLifecycle.apply(orderRejectedEvent);
    }
    
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) throws Exception {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }
    
    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent) throws Exception {
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }
    
    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent) throws Exception {
        this.orderStatus = orderRejectedEvent.getOrderStatus();
    }
 

}
