package com.coursenet.springbasic.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coursenet.springbasic.dto.OrderRequestDTO;
import com.coursenet.springbasic.dto.OrderResponseDTO;
import com.coursenet.springbasic.service.OrderService;

@RestController
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest){
		log.info(orderRequest.toString());
		return orderService.createOrder(orderRequest);
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
			@RequestBody OrderRequestDTO orderRequestDTO
	){
		return orderService.putOrder(id,orderRequestDTO);
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable(value="id") Long id){
		return orderService.deleteOrder(id);
	}
}
