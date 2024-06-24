package com.sparta.legendofdelivery.domain.user.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.oauth.dto.KakaoUserDto;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.global.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
public class OAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    public OAuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, RestTemplate restTemplate, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public HttpHeaders kakaoLogin(String code, String CLIENT_ID) throws JsonProcessingException {

        String token = getToken(code, CLIENT_ID);

        KakaoUserDto kakaoUserDto = getKakaoUserInfo(token);

        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserDto);

        String accessToken = jwtProvider.createAccessToken(kakaoUser.getUserId(), kakaoUser.getRole().getRole());
        String refreshToken = jwtProvider.createRefreshToken(kakaoUser.getUserId(), kakaoUser.getRole().getRole());
        ResponseCookie responseCookie = jwtProvider.createCookieRefreshToken(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

        kakaoUser.updateRefreshToken(refreshToken);
        userRepository.save(kakaoUser);

        return headers;
    }

    private String getToken(String code, String CLIENT_ID) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

        return jsonNode.get("access_token").asText();
    }

    private KakaoUserDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return new KakaoUserDto(id, nickname, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserDto kakaoUserDto) {

        User kakaoUser = userRepository.findByKakaoId(kakaoUserDto.getId()).orElse(null);

        if (kakaoUser == null) {

            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            kakaoUser = new User(kakaoUserDto, encodedPassword, UserRole.USER, UserStatus.NORMAL, UserOauth.KAKAO);

        }

        return kakaoUser;
    }

}
