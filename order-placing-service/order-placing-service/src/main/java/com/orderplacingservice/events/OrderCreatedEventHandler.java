package com.orderplacingservice.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.commonservice.events.OrderCompletedEvent;
import com.orderplacingservice.data.OrderEntity;
import com.orderplacingservice.data.OrderRepo;

@Component
public class OrderCreatedEventHandler {
	
	private OrderRepo orderRepo;
	
	public OrderCreatedEventHandler(OrderRepo orderRepo) {
		
		this.orderRepo = orderRepo;
	}

	@EventHandler
	public void on(OrderCreatedEvent createdEvent) {
		
		OrderEntity entity=new OrderEntity();
		BeanUtils.copyProperties(createdEvent, entity);
		orderRepo.save(entity);
	}
	
	  @EventHandler
	    public void on(OrderCompletedEvent event) {
		  OrderEntity order
	                = orderRepo.findById(event.getOrderId()).get();

	        order.setOrderStatus(event.getOrderStatus());

	        orderRepo.save(order);
	    }

}
