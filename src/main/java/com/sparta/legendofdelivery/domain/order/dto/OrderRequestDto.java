package com.sparta.legendofdelivery.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long storeId;

    @NotNull
    private Integer count;

}
