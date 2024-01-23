package com.shipmentservice.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.commonservice.events.OrderShippedEvent;
import com.shipmentservice.data.ShipmentEntity;
import com.shipmentservice.data.ShipmentRepository;

@Component
public class ShipmentsEventHandler {
	
    private ShipmentRepository shipmentRepository;

    public ShipmentsEventHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
    	ShipmentEntity shipment
                = new ShipmentEntity();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }

}
