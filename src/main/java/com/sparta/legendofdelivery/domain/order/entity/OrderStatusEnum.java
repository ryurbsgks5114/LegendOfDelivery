package com.sparta.legendofdelivery.domain.order.entity;

public enum OrderStatusEnum {
    ACCEPTANCE("ACCEPTANCE"),
    COMPLETE("COMPLETE");

    private String status;

    OrderStatusEnum(String type) { this.status = status; }

}
