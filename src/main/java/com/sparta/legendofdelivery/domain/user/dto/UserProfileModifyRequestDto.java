package com.sparta.legendofdelivery.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserProfileModifyRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식을 확인해 주세요.")
    private String email;

    private String name;

}
