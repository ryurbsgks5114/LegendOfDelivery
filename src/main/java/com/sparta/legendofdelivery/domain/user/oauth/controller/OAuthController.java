package com.sparta.legendofdelivery.domain.user.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.legendofdelivery.domain.user.oauth.service.OAuthService;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OAuthController {

    @Value("${kakao.rest.api.key}")
    private String CLIENT_ID;
    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String REDIRECT_URI = "http://localhost:8080/api/user/kakao/callback";

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/api/users/login/kakao")
    public void redirectKakaoLogin(HttpServletResponse response) throws IOException {

        String redirectURL = KAKAO_AUTH_URL + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
        response.sendRedirect(redirectURL);

    }

    @GetMapping("/api/user/kakao/callback")
    public ResponseEntity<MessageResponse> kakaoLogin(@RequestParam String code) throws JsonProcessingException {

        HttpHeaders headers = oAuthService.kakaoLogin(code, CLIENT_ID);
        MessageResponse response = new MessageResponse(200, "카카오 로그인에 성공했습니다.");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

}
