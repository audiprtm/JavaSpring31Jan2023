package com.coursenet.springbasic.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		System.out.println(orderRequest.getGoodsName() + orderRequest.getReceiverAddress() + orderRequest.getReceiverName());
		
		return orderService.createOrder(orderRequest);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponseDTO>> getOrders(
			@RequestParam(value="id", required=false) Long id,
			@RequestParam(value="name", required=false) String name
			){
		return orderService.getOrder(id,name);
	}
	
}
