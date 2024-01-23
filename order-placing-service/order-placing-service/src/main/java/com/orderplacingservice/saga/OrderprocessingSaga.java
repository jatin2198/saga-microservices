package com.orderplacingservice.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.commonservice.commands.CompleteOrderCommand;
import com.commonservice.commands.ShipOrderCmd;
import com.commonservice.commands.ValidatePaymentCmd;
import com.commonservice.events.OrderCompletedEvent;
import com.commonservice.events.OrderShippedEvent;
import com.commonservice.events.PaymentProceesedEvent;
import com.commonservice.models.User;
import com.commonservice.queries.GetUserDetailsQuery;
import com.orderplacingservice.events.OrderCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderprocessingSaga {

	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	


	
	public OrderprocessingSaga() {
		
		// TODO Auto-generated constructor stub
	}


	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent createdEvent) {
		
		log.info("Order created in saga= "+ createdEvent.getOrderId());
		
		GetUserDetailsQuery detailsQuery=new GetUserDetailsQuery(createdEvent.getUserId());
		User user=null;
		try {
		user=queryGateway.query(detailsQuery, ResponseTypes.instanceOf(User.class)).join();
		}catch (Exception e) {
			// TODO: handle exception
			log.info("Error occured while fetching User= "+e.getMessage());
			//start comensating cmd
		}
		
		ValidatePaymentCmd paymentCmd=ValidatePaymentCmd
				.builder()
				.cardDetails(user.getCardDetails())
				.orderId(createdEvent.getOrderId())
				.paymentId(UUID.randomUUID().toString())
				.build();
		
		commandGateway.sendAndWait(paymentCmd);
		
	} 
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(PaymentProceesedEvent event) {
	
		log.info("PaymentProceesedEvent created in saga= ");
		try {
			ShipOrderCmd shipOrderCmd=ShipOrderCmd.builder()
					.shipmentId(UUID.randomUUID().toString())
					.orderId(event.getOrderId())
					.build();
			commandGateway.sendAndWait(shipOrderCmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Error occured in Payment saga= "+e.getMessage());
		}
	}
	
	   @SagaEventHandler(associationProperty = "orderId")
	    public void handle(OrderShippedEvent event) {

	        log.info("OrderShippedEvent in Saga for Order Id : {}",
	                event.getOrderId());

	        CompleteOrderCommand completeOrderCommand
	                = CompleteOrderCommand.builder()
	                .orderId(event.getOrderId())
	                .orderStatus("APPROVED")
	                .build();

	        commandGateway.send(completeOrderCommand);
	    }
	   
	   @SagaEventHandler(associationProperty = "orderId")
	   @EndSaga
	    public void handle(OrderCompletedEvent event) {
	        log.info("OrderCompletedEvent in Saga for Order Id : {}",
	                event.getOrderId());
	    }
}
