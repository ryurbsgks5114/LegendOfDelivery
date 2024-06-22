package com.sparta.legendofdelivery.domain.order.controller;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderStatusRequestDto;
import com.sparta.legendofdelivery.domain.order.service.OrderService;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final StoreService storeService;

    public OrderController(OrderService orderService, StoreService storeService) {
        this.orderService = orderService;
        this.storeService = storeService;
    }

    // 주문 생성
    @PostMapping("/users/orders")
    public ResponseEntity<DataResponse<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        DataResponse<OrderResponseDto> response = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 주문 접수 상태 변경
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @Valid @RequestBody OrderStatusRequestDto requestDto) {

            DataResponse<OrderResponseDto> response = orderService.updateOrderStatus(orderId, requestDto.getUpdateStatus());
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 사용자별 전체 주문 목록 조회
    @GetMapping("/users/orders")
    public ResponseEntity<DataResponse<List<OrderResponseDto>>> getAllOrderByUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "createAt") String sortBy) {
        DataResponse<List<OrderResponseDto>> response = orderService.getAllOrderByUser(page - 1, size, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 주문 수정
    @PutMapping("/users/orders/{orderId}")
    public ResponseEntity<DataResponse<OrderResponseDto>> updateOrder(@Valid @RequestBody OrderRequestDto requestDto,
                                                                      @PathVariable Long orderId) {
        DataResponse<OrderResponseDto> response = orderService.updateOrder(orderId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 주문 삭제
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrder(orderId));
    }

    public ResponseEntity<MessageResponse> deleteStore(@PathVariable Long id) {

        return ResponseEntity.ok(storeService.deleteStore(id));
    }
}
