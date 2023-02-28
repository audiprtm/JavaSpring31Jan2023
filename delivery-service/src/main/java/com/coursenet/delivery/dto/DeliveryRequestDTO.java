package com.coursenet.delivery.dto;

import lombok.Data;

@Data
public class DeliveryRequestDTO {
    private Long orderId;
    private String receiverAddress;
    private String invoice;
    private Integer shipperId;
}
