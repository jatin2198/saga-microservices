package com.paymentservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.commonservice.commands.ValidatePaymentCmd;
import com.commonservice.events.PaymentProceesedEvent;
import com.commonservice.models.CardDetails;


import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class PaymentAggregate {
	
	@AggregateIdentifier
   private String paymentId;
	
	private String orderId;
	
	private CardDetails cardDetails;
	
	private String paymentStatus;

	public PaymentAggregate() {
		
		// TODO Auto-generated constructor stub
	}
	
	@CommandHandler
   public PaymentAggregate(ValidatePaymentCmd paymentCmd) {
		
		
		log.info("Executing Validate Payment Command for Order Id = "
				+ ""+paymentCmd.getOrderId() +" and payment ID= "
				+paymentCmd.getPaymentId());
	
		PaymentProceesedEvent paymentProceesedEvent=new PaymentProceesedEvent(paymentCmd.getPaymentId(), paymentCmd.getOrderId());	
	
	AggregateLifecycle.apply(paymentProceesedEvent);
	log.info("paymentProceesedEvent is applied");
	}
	
	@EventSourcingHandler
	public void on(PaymentProceesedEvent event) {
		this.orderId=event.getOrderId();
		this.paymentId=event.getPaymentId();
		
		
	}
	

}
