package com.coursenet.order.dto;

import com.coursenet.order.entity.Orders;
import lombok.Data;

@Data
public class OrderResponseDTO {
	
	public OrderResponseDTO(Orders order) {
		this.goodsName = order.getGoodsName();
		this.receiverName = order.getReceiverName();
		this.receiverAddress = order.getReceiverAddress();
		this.shipperId = order.getShipperId();
		this.invoice= order.getInvoice();
		this.status= order.getStatus();
	}

	public OrderResponseDTO() {

	}
	
	private String goodsName;
	private String receiverName;
	private String receiverAddress;
	private int shipperId;
	private String invoice;
	private String status;
}
