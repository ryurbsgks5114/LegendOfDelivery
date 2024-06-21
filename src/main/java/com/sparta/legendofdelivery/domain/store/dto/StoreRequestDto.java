package com.sparta.legendofdelivery.domain.store.dto;

import com.sparta.legendofdelivery.domain.store.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StoreRequestDto {

    @NotBlank(message = "가게 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "카테고리를 입력해주세요.")
    private Category category;

    @NotBlank(message = "가게 소개를 입력해주세요.")
    private String intro;

}
