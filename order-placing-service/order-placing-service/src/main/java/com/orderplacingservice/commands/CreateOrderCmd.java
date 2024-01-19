package com.orderplacingservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCmd {

	@TargetAggregateIdentifier
	private String orderId;
	private String productId;
	private String userId;
	private String addressId;
	private String quantity;
	private String orderStatus;
}
