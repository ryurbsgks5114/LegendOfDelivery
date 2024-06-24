package com.sparta.legendofdelivery.domain.order.dto;

import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {

    private Long id;
    private Long userId; // 회원 id
    private Long storeId; // 가게 id
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private OrderStatusEnum orderStatus; // 주문 접수, 주문 완료
    private Integer count; // 주문 수
    private Long totalPrice; // 가게 * 주문 수

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.createAt = order.getCreateAt();
        this.modifiedAt = order.getModifiedAt();
        this.orderStatus = order.getOrderStatus();
        this.count = order.getCount();
        this.totalPrice = order.getTotalPrice();
    }

}
