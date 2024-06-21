package com.sparta.legendofdelivery.domain.order.controller;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import com.sparta.legendofdelivery.domain.order.service.OrderService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/stores/{storeId}/orders/{userId}")
    public ResponseEntity<DataResponse<OrderResponseDto>> postOrder(@PathVariable Long storeId,
                                                                    @PathVariable Long userId,
                                                                    @Valid @RequestBody OrderRequestDto requestDto) {

        DataResponse<OrderResponseDto> response = orderService.createOrder(storeId, userId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 주문 접수 상태 변경
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<DataResponse<OrderResponseDto>> updateOrderStatus(@PathVariable Long orderId,
                                                                            @RequestParam OrderStatusEnum updateStatus) {
        DataResponse<OrderResponseDto> response = orderService.updateOrderStatus(orderId, updateStatus);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 사용자별 전체 주문 목록 조회
    @GetMapping("/{userId}/orders")
    public ResponseEntity<DataResponse<List<OrderResponseDto>>> getAllOrderByUser(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy) {

        DataResponse<List<OrderResponseDto>> response = orderService.getAllOrderByClient(userId, page - 1, size, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}