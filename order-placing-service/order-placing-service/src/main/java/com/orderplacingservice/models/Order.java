package com.orderplacingservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	private String productId;
	private String userId;
	private String addressId;
	private String quantity;
}
