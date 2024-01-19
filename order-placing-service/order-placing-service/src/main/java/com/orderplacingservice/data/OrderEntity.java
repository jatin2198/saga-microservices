package com.orderplacingservice.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

	@Id
	private String orderId;
	private String productId;
	private String userId;
	private String addressId;
	private String quantity;
	private String orderStatus;
}
