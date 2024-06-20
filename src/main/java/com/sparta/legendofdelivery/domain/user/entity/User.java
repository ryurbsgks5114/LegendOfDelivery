package com.sparta.legendofdelivery.domain.user.entity;

import com.sparta.legendofdelivery.domain.user.dto.UserSignupRequestDto;
import com.sparta.legendofdelivery.global.entity.Timestamped;
import com.sparta.legendofdelivery.global.enumeration.UserOauth;
import com.sparta.legendofdelivery.global.enumeration.UserRole;
import com.sparta.legendofdelivery.global.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column
    private String refreshToken;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserOauth oauth;

    public User(UserSignupRequestDto requestDto, UserRole role, UserStatus status, UserOauth oauth) {
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.role = role;
        this.status = status;
        this.oauth = oauth;
    }

}
