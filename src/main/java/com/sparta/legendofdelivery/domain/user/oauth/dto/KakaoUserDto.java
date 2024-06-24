package com.sparta.legendofdelivery.domain.user.oauth.dto;

import lombok.Getter;

@Getter
public class KakaoUserDto {

    private Long id;
    private String nickname;
    private String email;

    public KakaoUserDto(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

}
