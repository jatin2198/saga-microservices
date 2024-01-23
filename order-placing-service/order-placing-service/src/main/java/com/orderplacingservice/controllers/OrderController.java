package com.orderplacingservice.controllers;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderplacingservice.commands.CreateOrderCmd;
import com.orderplacingservice.models.Order;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	


	@PostMapping("/create")
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
