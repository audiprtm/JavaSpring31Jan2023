package com.coursenet.springbasic.dto;

import com.coursenet.springbasic.entity.Orders;
import lombok.Data;

@Data
public class OrderResponseDTO {
	
	public OrderResponseDTO(Orders order) {
		this.goodsName = order.getGoodsName();
		this.receiverName = order.getReceiverName();
		this.receiverAddress = order.getReceiverAddress();
	}

	public OrderResponseDTO() {

	}
	
	private String goodsName;
	private String receiverName;
	private String receiverAddress;

	public OrderResponseDTO() {

	}
}
