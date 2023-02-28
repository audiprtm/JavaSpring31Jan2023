package com.coursenet.order.dto;

import com.coursenet.order.entity.Orders;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
	
	public OrderResponseDTO(Orders order) {
		this.id=order.getId();
		this.createdAt=order.getCreatedAt();
		this.updatedAt=order.getUpdatedAt();
		this.goodsName = order.getGoodsName();
		this.receiverName = order.getReceiverName();
		this.receiverAddress = order.getReceiverAddress();
		this.shipperId = order.getShipperId();
		this.invoice= order.getInvoice();
		this.status= order.getStatus();
	}

	public OrderResponseDTO() {

	}

	private long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String goodsName;
	private String receiverName;
	private String receiverAddress;
	private int shipperId;
	private String invoice;
	private String status;
}
