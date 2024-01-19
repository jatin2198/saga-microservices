package com.paymentservice.events;

import java.time.LocalDate;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.commonservice.events.PaymentProceesedEvent;
import com.paymentservice.data.PaymentEntity;
import com.paymentservice.data.PaymentRepo;


@Component
public class PaymentEventHandler {

	private PaymentRepo paymentRepo;
	
	
	
	public PaymentEventHandler(PaymentRepo paymentRepo) {
	
		this.paymentRepo = paymentRepo;
	}



	@EventHandler
	public void on(PaymentProceesedEvent payEvent) {
		
		PaymentEntity entity=PaymentEntity.builder()
				.orderId(payEvent.getOrderId())
				.paymentId(payEvent.getPaymentId())
				.timestamp(LocalDate.now())
				.paymentStatus("CMPLED")
				.build();
		paymentRepo.save(entity);
	}
}
