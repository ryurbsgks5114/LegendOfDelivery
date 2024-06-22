package com.sparta.legendofdelivery.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private Long storeId;
    private Integer count;


}
