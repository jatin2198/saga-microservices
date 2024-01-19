package com.orderplacingservice.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {

	
	private String orderId;
	private String productId;
	private String userId;
	private String addressId;
	private String quantity;
	private String orderStatus;
}
