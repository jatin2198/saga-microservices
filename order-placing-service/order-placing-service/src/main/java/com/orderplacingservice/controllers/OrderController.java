package com.orderplacingservice.controllers;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderplacingservice.commands.CreateOrderCmd;
import com.orderplacingservice.models.Order;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private CommandGateway commandGateway;
	
	public OrderController(CommandGateway commandGateway) {
		
	this.commandGateway = commandGateway;
	}

	public ResponseEntity createOrder(@RequestBody Order order){
		
		String orderId=UUID.randomUUID().toString();
		
		CreateOrderCmd cmd=CreateOrderCmd.builder()
				.orderId(orderId)
				.productId(order.getProductId())
				.addressId(order.getAddressId())
				.quantity(order.getQuantity())
				.userId(order.getUserId())
				.orderStatus("CRTED")
				
		         
			.build();
		
		commandGateway.sendAndWait(cmd);
		return ResponseEntity.ok("Order Created..");
		
		
	}

}
