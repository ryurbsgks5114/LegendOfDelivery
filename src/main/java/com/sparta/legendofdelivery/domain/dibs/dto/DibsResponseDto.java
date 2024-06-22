package com.sparta.legendofdelivery.domain.dibs.dto;

import com.sparta.legendofdelivery.domain.dibs.entity.Dibs;
import com.sparta.legendofdelivery.domain.store.entity.Category;
import lombok.Getter;


@Getter
public class DibsResponseDto {

    private final Long storeId;
    private final String storeName;
    private final Category category;
    private final Long dibsCount;

    public DibsResponseDto(Long storeId, String storeName, Category category, Long dibsCount) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.category = category;
        this.dibsCount = dibsCount;
    }

    public static DibsResponseDto toDto(Dibs dibs) {
        return new DibsResponseDto(dibs.getStore().getId(), dibs.getStore().getName(), dibs.getStore().getCategory(), dibs.getStore().getDibsCount());
    }

}
