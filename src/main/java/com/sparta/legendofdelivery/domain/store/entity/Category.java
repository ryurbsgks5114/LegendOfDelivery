package com.sparta.legendofdelivery.domain.store.entity;

import lombok.Getter;

@Getter
public enum Category {

    KOREAN(15000),
    PIZZA(20000),
    CHINA(12000),
    BURGER(10000);

    private final int price;

    Category(int price) {
        this.price = price;
    }

}
