package com.orderplacingservice.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.commonservice.commands.CompleteOrderCommand;
import com.commonservice.events.OrderCompletedEvent;
import com.orderplacingservice.commands.CreateOrderCmd;
import com.orderplacingservice.events.OrderCreatedEvent;

@Aggregate
public class OrderAggregate {
	
	@AggregateIdentifier
	private String orderId;
	private String productId;
	private String userId;
	private String addressId;
	private String quantity;
	private String orderStatus;
	
	public OrderAggregate() {
	
	}
	
	//command handler
	
	@CommandHandler
   public OrderAggregate(CreateOrderCmd cmd) {
		
		//here u can add bussiness logic to validate commands 
		OrderCreatedEvent createdEvent=new OrderCreatedEvent();
		BeanUtils.copyProperties(cmd, createdEvent);
		
		AggregateLifecycle.apply(createdEvent);
	
	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent event) {
		this.orderId=event.getOrderId();
		this.productId=event.getProductId();
		this.userId=event.getUserId();
		this.addressId=event.getAddressId();
		this.quantity=event.getQuantity();
		this.orderStatus=event.getOrderStatus();
	}
	
	@CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        //Validate The Command
        // Publish Order Completed Event
        OrderCompletedEvent orderCompletedEvent
                = OrderCompletedEvent.builder()
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderId(completeOrderCommand.getOrderId())
                .build();
        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.orderStatus = event.getOrderStatus();
    }


}
