package com.sparta.legendofdelivery.domain.order.controller;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.service.OrderService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/stores/{storeId}/orders/{userId}")
    public ResponseEntity<DataResponse<Order>> postOrder(@PathVariable Long storeId,
                                                         @PathVariable Long userId,
                                                         @Valid @RequestBody OrderRequestDto requestDto) {

        DataResponse<Order> response = orderService.createOrder(storeId, userId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}