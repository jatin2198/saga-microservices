package com.commonservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProceesedEvent {
	
	private String paymentId;
	private String orderId;

}
