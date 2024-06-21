package com.sparta.legendofdelivery.domain.order.dto;

import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {
    private OrderStatusEnum updateStatus;

    public OrderStatusRequestDto() {}

    @Builder
    public OrderStatusRequestDto(OrderStatusEnum updateStatus) {
        this.updateStatus = updateStatus;
    }
}
