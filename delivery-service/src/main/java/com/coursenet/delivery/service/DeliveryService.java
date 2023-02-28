package com.coursenet.delivery.service;

import com.coursenet.delivery.constants.DeliveryStatusConstants;
import com.coursenet.delivery.dto.DeliveryRequestDTO;
import com.coursenet.delivery.dto.DeliveryResponseDTO;
import com.coursenet.delivery.entity.Delivery;
import com.coursenet.delivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    public ResponseEntity<DeliveryResponseDTO> createDelivery(DeliveryRequestDTO deliveryRequest) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(deliveryRequest.getOrderId());
        delivery.setInvoice(deliveryRequest.getInvoice());
        delivery.setShipperId(deliveryRequest.getShipperId());
        delivery.setReceiverAddress(deliveryRequest.getReceiverAddress());

        delivery.setStatus(DeliveryStatusConstants.DELIVERY_CREATED);
        delivery = deliveryRepository.save(delivery);

        DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO(delivery);
        return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.CREATED);
    }
}
