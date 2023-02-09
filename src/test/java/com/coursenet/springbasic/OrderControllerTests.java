package com.coursenet.springbasic;

import com.coursenet.springbasic.controller.OrderController;
import com.coursenet.springbasic.dto.OrderRequestDTO;
import com.coursenet.springbasic.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private OrderService orderService;

    @Test
    void test_PostOrderController_Success() throws Exception {
        //AAA

        //Arrange
        //Deklarasi Variabel untuk Di Act
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setGoodsName("Laptop");
        orderRequestDTO.setReceiverName("Audi");
        orderRequestDTO.setReceiverAddress("BSD");

        //Act
        mockMvc.perform(
                post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDTO))
        )
        //Assert
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    }

}
