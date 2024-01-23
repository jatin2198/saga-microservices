package com.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.commonservice.models.CardDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipOrderCmd {
	@TargetAggregateIdentifier
	private String shipmentId;
	
	private String orderId;
}
