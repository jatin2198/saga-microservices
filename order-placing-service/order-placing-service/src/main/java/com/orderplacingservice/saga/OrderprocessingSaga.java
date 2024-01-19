package com.orderplacingservice.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.commonservice.commands.ValidatePaymentCmd;
import com.commonservice.events.PaymentProceesedEvent;
import com.commonservice.models.User;
import com.commonservice.queries.GetUserDetailsQuery;
import com.orderplacingservice.events.OrderCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderprocessingSaga {

	private CommandGateway commandGateway;
	
	private QueryGateway queryGateway;
	
	@Autowired
	public OrderprocessingSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
		
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
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
		
	}
}
