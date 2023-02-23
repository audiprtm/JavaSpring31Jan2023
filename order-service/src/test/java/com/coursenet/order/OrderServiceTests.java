package com.coursenet.order;

import com.coursenet.order.dto.OrderRequestDTO;
import com.coursenet.order.dto.OrderResponseDTO;
import com.coursenet.order.entity.Orders;
import com.coursenet.order.repository.OrderRepository;
import com.coursenet.order.repository.OrderRepositoryCustom;
import com.coursenet.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderRepositoryCustom orderRepositoryCustom;

    @Test
    public void test_CreateOrderService_Success() throws Exception{
        //Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setGoodsName("Laptop");
        mockOrder.setReceiverAddress("BSD");
        mockOrder.setReceiverName("Audi");

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setGoodsName("Laptop");
        orderRequestDTO.setReceiverAddress("BSD");
        orderRequestDTO.setReceiverName("Audi");

        when(orderRepository.save(Mockito.any())).thenReturn(mockOrder);

        //Act
        ResponseEntity<OrderResponseDTO> actualResponse = orderService.createOrder(orderRequestDTO);

        //Assert
        assertEquals(actualResponse.getBody().getGoodsName(),orderRequestDTO.getGoodsName());
        assertEquals(actualResponse.getBody().getReceiverName(),orderRequestDTO.getReceiverName());
        assertEquals(actualResponse.getBody().getReceiverAddress(),orderRequestDTO.getReceiverAddress());
    }

    @Test
    public void test_GetOrderServiceById_Success() throws Exception{
        //Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setGoodsName("Laptop");
        mockOrder.setReceiverAddress("BSD");
        mockOrder.setReceiverName("Audi");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        //Act
        ResponseEntity<List<OrderResponseDTO>> listActualResponse =
                orderService.getOrder(1L,null,null);

        //Assert
        assertEquals(listActualResponse.getBody().get(0).getGoodsName(), mockOrder.getGoodsName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverName(), mockOrder.getReceiverName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverAddress(), mockOrder.getReceiverAddress());
    }

    @Test
    public void test_GetOrderServiceAll_Success() throws Exception{
        //Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setGoodsName("Laptop");
        mockOrder.setReceiverAddress("BSD");
        mockOrder.setReceiverName("Audi");

        List<Orders> listMockOrder = new ArrayList<>();
        listMockOrder.add(mockOrder);

        when(orderRepository.findAll()).thenReturn(listMockOrder);

        //Act
        ResponseEntity<List<OrderResponseDTO>> listActualResponse =
                orderService.getOrder(null,null,null);

        //Assert
        assertEquals(listActualResponse.getBody().get(0).getGoodsName(), mockOrder.getGoodsName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverName(), mockOrder.getReceiverName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverAddress(), mockOrder.getReceiverAddress());
    }

    @Test
    public void test_GetOrderServiceCustomQuery_ByName_Success() throws Exception{
        //Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setGoodsName("Laptop");
        mockOrder.setReceiverAddress("BSD");
        mockOrder.setReceiverName("Audi");

        List<Orders> listMockOrder = new ArrayList<>();
        listMockOrder.add(mockOrder);

        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("goodsName","Laptop");

        when(orderRepositoryCustom.findOrderByDynamicField(queryParam)).thenReturn(listMockOrder);

        //Act
        ResponseEntity<List<OrderResponseDTO>> listActualResponse =
                orderService.getOrder(null,"Laptop",null);

        //Assert
        assertEquals(listActualResponse.getBody().get(0).getGoodsName(), mockOrder.getGoodsName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverName(), mockOrder.getReceiverName());
        assertEquals(listActualResponse.getBody().get(0).getReceiverAddress(), mockOrder.getReceiverAddress());
    }

    @Test
    public void test_GetOrderByID_NotFound() throws Exception{
        //Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        //Act
        ResponseEntity<List<OrderResponseDTO>> listActualResponse =
                orderService.getOrder(1L,null,null);

        //Assert
        assertEquals(listActualResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void test_DeleteOrderByID_Success() throws Exception{
        //Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(new Orders()));

        //Act
        ResponseEntity<OrderResponseDTO> listActualResponse = orderService.deleteOrder(1L);

        //Assert
        verify(orderRepository).deleteById(1L);
        assertEquals(listActualResponse.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void test_PutOrderByID_Success() throws Exception{
        //Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setGoodsName("Laptop");
        mockOrder.setReceiverAddress("BSD");
        mockOrder.setReceiverName("Audi");

        Orders editOrder = new Orders();
        editOrder.setId(1L);
        editOrder.setGoodsName("Laptop");
        editOrder.setReceiverAddress("BSD");
        editOrder.setReceiverName("Anggawa");

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setGoodsName("Laptop");
        orderRequestDTO.setReceiverAddress("BSD");
        orderRequestDTO.setReceiverName("Anggawa");

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(mockOrder));
        when(orderRepository.save(Mockito.any())).thenReturn(editOrder);

        //Act
        ResponseEntity<OrderResponseDTO> actualResponse = orderService.putOrder(1L,orderRequestDTO);

        //Assert
        assertEquals(actualResponse.getBody().getGoodsName(),editOrder.getGoodsName());
        assertEquals(actualResponse.getBody().getReceiverName(),editOrder.getReceiverName());
        assertEquals(actualResponse.getBody().getReceiverAddress(),editOrder.getReceiverAddress());
    }
}
