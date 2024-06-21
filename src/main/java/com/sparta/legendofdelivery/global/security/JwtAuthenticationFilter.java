package com.sparta.legendofdelivery.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.legendofdelivery.domain.user.dto.UserLoginRequestDto;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("잘못된 http 요청입니다.");
        }

        try {

            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUserId(), requestDto.getPassword(), null));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String userId = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty() || user.get().getStatus().equals(UserStatus.LEAVE)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            MessageResponse messageResponse = new MessageResponse(400, "아이디, 비밀번호를 확인해주세요.");
            String json = new ObjectMapper().writeValueAsString(messageResponse);
            response.getWriter().write(json);
        }

        String accessToken = jwtProvider.createAccessToken(userId, role);
        String refreshToken = jwtProvider.createRefreshToken(userId, role);
        ResponseCookie refreshTokenCookie = jwtProvider.createCookieRefreshToken(refreshToken);

        user.get().updateRefreshToken(refreshToken);

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        MessageResponse messageResponse = new MessageResponse(200, "로그인에 성공했습니다.");
        String json = new ObjectMapper().writeValueAsString(messageResponse);
        response.getWriter().write(json);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");
        MessageResponse messageResponse = new MessageResponse(400, "아이디, 비밀번호를 확인해주세요.");
        String json = new ObjectMapper().writeValueAsString(messageResponse);
        response.getWriter().write(json);
    }

}
