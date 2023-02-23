package com.coursenet.order.controller;

import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.dto.OrderStatusRequestDTO;
import com.coursenet.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequest){
		log.info("Create Order Started: "+ orderRequest.toString());
		return orderService.createOrder(orderRequest);
	}

	@PostMapping("/orders/update-status")
	public ResponseEntity<OrderResponseDTO> updateStatusOrder(@RequestBody @Valid OrderStatusRequestDTO orderRequest){
		log.info("Update Status Order Started: "+orderRequest.toString());
		return orderService.updateStatusOrder(orderRequest);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponseDTO>> getOrders(
			@RequestParam(value="id", required=false) Long id,
			@RequestParam(value="goodsName", required=false) String goodsName,
			@RequestParam(value="receiverName", required=false) String receiverName
			){
		return orderService.getOrder(id,goodsName,receiverName);
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDTO> putOrders(
			@PathVariable(value="id") Long id,
			@RequestBody @Valid OrderRequestDTO orderRequestDTO
	){
		return orderService.putOrder(id,orderRequestDTO);
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable(value="id") Long id){
		return orderService.deleteOrder(id);
	}
}
