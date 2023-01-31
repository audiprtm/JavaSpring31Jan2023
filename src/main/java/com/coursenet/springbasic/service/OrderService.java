package com.coursenet.springbasic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coursenet.springbasic.dto.OrderRequestDTO;
import com.coursenet.springbasic.dto.OrderResponseDTO;
import com.coursenet.springbasic.entity.Orders;
import com.coursenet.springbasic.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public ResponseEntity<OrderResponseDTO> createOrder(OrderRequestDTO orderRequest) {
		Orders order = new Orders();
		order.setGoodsName(orderRequest.getGoodsName());
		order.setReceiverAddress(orderRequest.getReceiverAddress());
		order.setReceiverName(orderRequest.getReceiverName());
		
		order = orderRepository.save(order);
		
		OrderResponseDTO orderResponse = new OrderResponseDTO(order);
		
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED) ;
	}

	public ResponseEntity<List<OrderResponseDTO>> getOrder(Long id, String name) {
		List<OrderResponseDTO> listOrderResponseDTO = new ArrayList<>();
		
		//Get All
		if(id==null) {
			List<Orders> listOrders= orderRepository.findAll();
			
			for(int i=0;i<listOrders.size();i++) {
				OrderResponseDTO orderResponse = new OrderResponseDTO(listOrders.get(i));
				listOrderResponseDTO.add(orderResponse);
			}
		}else if(name!=null) {//Get By Goods Name
			Optional<Orders> order= orderRepository.findByGoodsName(name);
			if(!order.isPresent()) {
				return new ResponseEntity<>(listOrderResponseDTO, HttpStatus.NOT_FOUND) ;
			}
			
			OrderResponseDTO orderResponse = new OrderResponseDTO(order.get());
			listOrderResponseDTO.add(orderResponse);
		}else { //Get By Id
			Optional<Orders> order= orderRepository.findById(id);
			if(!order.isPresent()) {
				return new ResponseEntity<>(listOrderResponseDTO, HttpStatus.NOT_FOUND) ;
			}
			
			OrderResponseDTO orderResponse = new OrderResponseDTO(order.get());
			listOrderResponseDTO.add(orderResponse);
		}
		
		return new ResponseEntity<>(listOrderResponseDTO, HttpStatus.OK) ;
	}

}
