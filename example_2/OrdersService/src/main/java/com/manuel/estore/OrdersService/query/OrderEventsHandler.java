/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manuel.estore.OrdersService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manuel.estore.OrdersService.core.data.OrderEntity;
import com.manuel.estore.OrdersService.core.data.OrdersRepository;
import com.manuel.estore.OrdersService.core.events.OrderApprovedEvent;
import com.manuel.estore.OrdersService.core.events.OrderCreatedEvent;
import com.manuel.estore.OrdersService.core.events.OrderRejectedEvent;
import com.manuel.estore.OrdersService.core.model.OrderStatus;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {
    
    private final OrdersRepository ordersRepository;
    
    public OrderEventsHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
 
        this.ordersRepository.save(orderEntity);
    }
    
    @EventHandler
    public void on(OrderApprovedEvent event) throws Exception {
        OrderEntity orderEntity = ordersRepository.findOrderByOrderId(event.getOrderId());
        orderEntity.setOrderStatus(OrderStatus.APPROVED);
        this.ordersRepository.save(orderEntity);
    }
    
    @EventHandler
    public void on(OrderRejectedEvent event) throws Exception {
        OrderEntity orderEntity = ordersRepository.findOrderByOrderId(event.getOrderId());
        orderEntity.setOrderStatus(OrderStatus.REJECTED);
        this.ordersRepository.save(orderEntity);
    }
    
}
