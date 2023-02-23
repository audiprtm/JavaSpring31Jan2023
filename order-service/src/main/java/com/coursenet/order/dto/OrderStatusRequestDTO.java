package com.coursenet.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderStatusRequestDTO {
    @NotNull
    private Long id;

    @NotNull
    private String status;
}
