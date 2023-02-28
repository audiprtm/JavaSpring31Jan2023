package com.coursenet.order.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryResponseDTO {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long orderId;
    private String invoice;
    private int shipperId;
    private String status;
    private String receiverAddress;
}
