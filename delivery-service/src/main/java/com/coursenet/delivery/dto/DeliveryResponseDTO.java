package com.coursenet.delivery.dto;

import com.coursenet.delivery.entity.Delivery;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeliveryResponseDTO {
    private long id;
    private LocalDateTime createdAt;

    public DeliveryResponseDTO(Delivery delivery) {
        this.id = delivery.getId();
        this.createdAt = delivery.getCreatedAt();
        this.updatedAt = delivery.getUpdatedAt();
        this.orderId = delivery.getOrderId();
        this.invoice = delivery.getInvoice();
        this.shipperId = delivery.getShipperId();
        this.status = delivery.getStatus();
        this.receiverAddress = delivery.getReceiverAddress();
    }

    private LocalDateTime updatedAt;
    private long orderId;
    private String invoice;
    private int shipperId;
    private String status;
    private String receiverAddress;
}
