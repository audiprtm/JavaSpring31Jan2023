package com.coursenet.springbasic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
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

	public ResponseEntity<OrderResponseDTO> putOrder(Long id, OrderRequestDTO orderRequestDTO) {
		//Cari id nya ada atau engga
		Optional<Orders> order = orderRepository.findById(id);

		//Kalo engga? Create baru
		if (!order.isPresent()){
			Orders newOrder = new Orders();
			newOrder.setId(id);
			newOrder.setGoodsName(orderRequestDTO.getGoodsName());
			newOrder.setReceiverAddress(orderRequestDTO.getReceiverAddress());
			newOrder.setReceiverName(orderRequestDTO.getReceiverName());

			newOrder = orderRepository.saveAndFlush(newOrder);

			OrderResponseDTO orderResponse = new OrderResponseDTO(newOrder);
			return new ResponseEntity<>(orderResponse, HttpStatus.CREATED) ;
		}

		//Kalo edit
		Orders editOrder = order.get();
		editOrder.setGoodsName(orderRequestDTO.getGoodsName());
		editOrder.setReceiverAddress(orderRequestDTO.getReceiverAddress());
		editOrder.setReceiverName(orderRequestDTO.getReceiverName());

		//Save
		editOrder = orderRepository.save(editOrder);
		return new ResponseEntity<>(new OrderResponseDTO(editOrder), HttpStatus.OK) ;
	}

	public ResponseEntity<OrderResponseDTO> deleteOrder(Long id) {
		//Find
		Optional<Orders> order = orderRepository.findById(id);
		if(!order.isPresent()){
			return new ResponseEntity<>(new OrderResponseDTO(), HttpStatus.NOT_FOUND);
		}

		//Delete
		orderRepository.deleteById(id);
		return new ResponseEntity<>(new OrderResponseDTO(), HttpStatus.NO_CONTENT);
	}
}
