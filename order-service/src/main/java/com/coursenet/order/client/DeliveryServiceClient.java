package com.coursenet.order.client;

import com.coursenet.order.dto.DeliveryRequestDTO;
import com.coursenet.order.dto.DeliveryResponseDTO;
import com.coursenet.order.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class DeliveryServiceClient {
    @Autowired
    private JWTUtil jwtUtil;

    @Value("${delivery.base.url}")
    private String deliveryBaseURL;

    @Value("${delivery.createDelivery.endpoint}")
    private String createDeliveryEndpoint;

    private RestTemplate restTemplate = new RestTemplate();

    public void createDelivery(DeliveryRequestDTO deliveryRequestDTO){
        log.info("Create Delivery Client Started: "+deliveryRequestDTO.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer "+jwtUtil.getCurrentToken());

        HttpEntity<DeliveryRequestDTO> request = new HttpEntity<>(deliveryRequestDTO,httpHeaders);

        DeliveryResponseDTO deliveryResponseDTO = restTemplate.postForObject(
                deliveryBaseURL+createDeliveryEndpoint,
                request,
                DeliveryResponseDTO.class
        );

        log.info("Create Delivery Client Finished: "+deliveryResponseDTO.toString());
    }
}
