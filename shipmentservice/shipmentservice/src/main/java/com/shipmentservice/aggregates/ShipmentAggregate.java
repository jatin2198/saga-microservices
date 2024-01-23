package com.shipmentservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.commonservice.commands.ShipOrderCmd;
import com.commonservice.events.OrderShippedEvent;

@Aggregate
public class ShipmentAggregate {
	@AggregateIdentifier
	private String shipmentId;
	private String orderId;
	private String shipmentStatus;
	public ShipmentAggregate() {
	}
	
	@CommandHandler
	public ShipmentAggregate(ShipOrderCmd shipOrderCmd) {
	
		  OrderShippedEvent orderShippedEvent
          = OrderShippedEvent
          .builder()
          .shipmentId(shipOrderCmd.getShipmentId())
          .orderId(shipOrderCmd.getOrderId())
          .shipmentStatus("COMPLETED")
          .build();

  AggregateLifecycle.apply(orderShippedEvent);
	}
	
    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
	
}
