package com.sparta.legendofdelivery.domain.order.controller;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderStatusRequestDto;
import com.sparta.legendofdelivery.domain.order.service.OrderService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/users/orders")
    public ResponseEntity<DataResponse<OrderResponseDto>> postOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @Valid @RequestBody OrderRequestDto requestDto) {

        User user = userDetails.getUser();
        DataResponse<OrderResponseDto> response = orderService.createOrder(user.getId(), requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 주문 접수 상태 변경
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @Valid @RequestBody OrderStatusRequestDto requestDto) {
        try {
            DataResponse<OrderResponseDto> response = orderService.updateOrderStatus(orderId, requestDto.getUpdateStatus());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(400, e.getMessage()));
        }
    }

    // 사용자별 전체 주문 목록 조회
    @GetMapping("/users/orders")
    public ResponseEntity<DataResponse<List<OrderResponseDto>>> getAllOrderByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy) {

        Long userId = userDetails.getUser().getId();
        DataResponse<List<OrderResponseDto>> response = orderService.getAllOrderByClient(userId, page - 1, size, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}