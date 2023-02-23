package com.coursenet.order.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderRequestDTO {
	@NotNull
	private String goodsName;
	@NotNull
	private String receiverName;
	@NotNull
	private String receiverAddress;
	@NotNull
	private int shipperId;
}
