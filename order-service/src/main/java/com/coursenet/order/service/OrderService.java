package com.coursenet.order.service;

import com.coursenet.order.client.DeliveryServiceClient;
import com.coursenet.order.constants.OrderStatusConstants;
import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.dto.OrderStatusRequestDTO;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.repository.OrderRepository;
import com.coursenet.order.repository.OrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderRepositoryCustom orderRepositoryCustom;

	@Autowired
	private DeliveryServiceClient deliveryServiceClient;
	
	public ResponseEntity<OrderResponseDTO> createOrder(OrderRequestDTO orderRequest) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String stringDate = dateFormat.format(new Date());

		Orders order = new Orders();
		order.setGoodsName(orderRequest.getGoodsName());
		order.setReceiverAddress(orderRequest.getReceiverAddress());
		order.setReceiverName(orderRequest.getReceiverName());
		order.setStatus(OrderStatusConstants.ORDER_CREATED);
		order.setShipperId(orderRequest.getShipperId());
		order.setInvoice("INVOICE/"+stringDate);
		
		order = orderRepository.save(order);
		
		OrderResponseDTO orderResponse = new OrderResponseDTO(order);
		
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED) ;
	}

	public ResponseEntity<List<OrderResponseDTO>> getOrder(Long id, String goodsName, String receiverName) {
		List<OrderResponseDTO> listOrderResponseDTO = new ArrayList<>();
		if (id!=null){ //Find by Id
			Optional<Orders> order= orderRepository.findById(id);
			if(!order.isPresent()) {
				return new ResponseEntity<>(listOrderResponseDTO, HttpStatus.NOT_FOUND) ;
			}

			OrderResponseDTO orderResponse = new OrderResponseDTO(order.get());
			listOrderResponseDTO.add(orderResponse);
		}else{
			//Generate Parameters
			Map<String, Object> mapQuery = new HashMap<>();
			String field="";
			Object value;

			//Add Parameter: Get By GoodsName
			if (goodsName!=null){
				field="goodsName";
				value = goodsName;
				mapQuery.put(field,value);
			}

			//Add Parameter: Get By ReceiverName
			if (receiverName!=null) {
				field = "receiverName";
				value = receiverName;
				mapQuery.put(field, value);
			}

			//Get Order from Paramter
			//1. If field is not null get from dynamic query
			//2. If field is null use default hibernate to get all orders
			List<Orders> listOrders;
			if (field!=""){ //Get from Dynamic Query
				listOrders=orderRepositoryCustom.findOrderByDynamicField(mapQuery);
			}else{ //Get All
				listOrders= orderRepository.findAll();
			}

			//Check order is exist or not
			if(listOrders==null || listOrders.size()==0) {
				return new ResponseEntity<>(listOrderResponseDTO, HttpStatus.NOT_FOUND) ;
			}

			//Map List Orders to Response DTO
			for(int i=0;i<listOrders.size();i++) {
				OrderResponseDTO orderResponse = new OrderResponseDTO(listOrders.get(i));
				listOrderResponseDTO.add(orderResponse);
			}
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

	public ResponseEntity<OrderResponseDTO> updateStatusOrder(OrderStatusRequestDTO orderRequest) {
		//Cari id nya ada atau engga
		Optional<Orders> order = orderRepository.findById(orderRequest.getId());
		if (!order.isPresent()){
			return new ResponseEntity<>(new OrderResponseDTO(), HttpStatus.NOT_FOUND) ;
		}

		Orders updateOrder=order.get();
		switch (orderRequest.getStatus()){
			case OrderStatusConstants.ORDER_INPROCESS:
				return processOrder(updateOrder);
			case OrderStatusConstants.ORDER_FINISHED:
				return finishOrder(updateOrder);
			case OrderStatusConstants.ORDER_CANCELLED:
				return cancelOrder(updateOrder);
			default:
				return new ResponseEntity<>(new OrderResponseDTO(), HttpStatus.BAD_REQUEST) ;
		}
	}

	private ResponseEntity<OrderResponseDTO> finishOrder(Orders updateOrder) {
		updateOrder.setStatus(OrderStatusConstants.ORDER_FINISHED);
		updateOrder = orderRepository.save(updateOrder);
		return new ResponseEntity<>(new OrderResponseDTO(updateOrder), HttpStatus.OK) ;
	}

	private ResponseEntity<OrderResponseDTO> cancelOrder(Orders updateOrder) {
		updateOrder.setStatus(OrderStatusConstants.ORDER_CANCELLED);
		updateOrder = orderRepository.save(updateOrder);
		return new ResponseEntity<>(new OrderResponseDTO(updateOrder), HttpStatus.OK) ;
	}

	private ResponseEntity<OrderResponseDTO> processOrder(Orders updateOrder) {
		//Build Delivery RequestDTO
		DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
		deliveryRequestDTO.setOrderId(updateOrder.getId());
		deliveryRequestDTO.setReceiverAddress(updateOrder.getReceiverAddress());
		deliveryRequestDTO.setShipperId(updateOrder.getShipperId());
		deliveryRequestDTO.setInvoice(updateOrder.getInvoice());

		//Tembak Create Delivery
		deliveryServiceClient.createDelivery(deliveryRequestDTO);

		//Update Status Order
		updateOrder.setStatus(OrderStatusConstants.ORDER_INPROCESS);
		updateOrder = orderRepository.save(updateOrder);
		return new ResponseEntity<>(new OrderResponseDTO(updateOrder), HttpStatus.OK) ;
	}
}
