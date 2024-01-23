package com.shipmentservice.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "shipment")
public class ShipmentEntity {
	   @Id
	    private String shipmentId;
	    private String orderId;
	    private String shipmentStatus;
}
