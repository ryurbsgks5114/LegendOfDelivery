package com.sparta.legendofdelivery.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private String message;
    private T data;

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
