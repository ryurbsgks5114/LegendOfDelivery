package com.sparta.legendofdelivery.domain.user.controller;

import com.sparta.legendofdelivery.domain.user.dto.UserSignupRequestDto;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {

        userService.signup(requestDto);

        return createResponseEntity(HttpStatus.CREATED, "회원가입에 성공했습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        userService.logout();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<MessageResponse> createResponseEntity(HttpStatus httpStatusCode, String message) {

        MessageResponse response = new MessageResponse(httpStatusCode.value(), message);

        return ResponseEntity.status(httpStatusCode).body(response);
    }

}
