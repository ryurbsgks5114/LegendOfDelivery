package com.sparta.legendofdelivery.domain.user.controller;

import com.sparta.legendofdelivery.domain.user.dto.*;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 단위 테스트")
    public void signup() {

        UserSignupRequestDto requestDto = mock(UserSignupRequestDto.class);
        MessageResponse response = new MessageResponse(201, "회원가입에 성공했습니다.");

        when(userService.signup(any(UserSignupRequestDto.class))).thenReturn(response);

        ResponseEntity<MessageResponse> responseEntity = userController.signup(requestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getStatusCode());
        assertEquals("회원가입에 성공했습니다.", responseEntity.getBody().getMessage());

    }

    @Test
    @DisplayName("로그아웃 단위 테스트")
    public void logout() {

        ResponseEntity<Void> response = userController.logout();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    @DisplayName("회원탈퇴 단위 테스트")
    public void withdrawal() {

        UserWithdrawalRequestDto requestDto = mock(UserWithdrawalRequestDto.class);

        ResponseEntity<Void> response = userController.withdrawal(requestDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    @DisplayName("토큰 재발급 단위 테스트")
    public void refreshToken() {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);

        when(userService.refreshToken(any(HttpServletRequest.class))).thenReturn(headers);

        ResponseEntity<MessageResponse> response = userController.refreshToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("토큰 재발급에 성공했습니다.", response.getBody().getMessage());

    }

    @Test
    @DisplayName("사용자 권한 업데이트 단위 테스트")
    public void updateUserRole() {

        MessageResponse response = new MessageResponse(200, "회원 권한 변경에 성공했습니다.");

        when(userService.updateUserRole()).thenReturn(response);

        ResponseEntity<MessageResponse> responseEntity = userController.updateUserRole();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, responseEntity.getBody().getStatusCode());
        assertEquals("회원 권한 변경에 성공했습니다.", responseEntity.getBody().getMessage());

    }

    @Test
    @DisplayName("프로필 조회 단위 테스트")
    public void getProfile() {

        DataResponse<UserProfileResponseDto> response = new DataResponse<>(200, "프로필 조회에 성공했습니다.", Mockito.mock(UserProfileResponseDto.class));

        when(userService.getProfile()).thenReturn(response);

        ResponseEntity<DataResponse<UserProfileResponseDto>> responseEntity = userController.getProfile();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, responseEntity.getBody().getStatusCode());
        assertEquals("프로필 조회에 성공했습니다.", responseEntity.getBody().getMessage());

    }

    @Test
    @DisplayName("프로필 수정 단위 테스트")
    public void modifyProfile() {

        UserProfileModifyRequestDto requestDto = Mockito.mock(UserProfileModifyRequestDto.class);
        DataResponse<UserProfileResponseDto> response = new DataResponse<>(200, "프로필 수정에 성공했습니다.", Mockito.mock(UserProfileResponseDto.class));

        when(userService.modifyProfile(any(UserProfileModifyRequestDto.class))).thenReturn(response);

        ResponseEntity<DataResponse<UserProfileResponseDto>> responseEntity = userController.modifyProfile(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, responseEntity.getBody().getStatusCode());
        assertEquals("프로필 수정에 성공했습니다.", responseEntity.getBody().getMessage());

    }

    @Test
    @DisplayName("프로필 비밀번호 변경 단위 테스트")
    public void updatePassword() {

        UserProfileUpdatePasswordRequestDto requestDto = Mockito.mock(UserProfileUpdatePasswordRequestDto.class);
        MessageResponse response = new MessageResponse(200, "비밀번호 수정에 성공했습니다.");

        when(userService.updatePassword(any(UserProfileUpdatePasswordRequestDto.class))).thenReturn(response);

        ResponseEntity<MessageResponse> responseEntity = userController.updatePassword(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(200, responseEntity.getBody().getStatusCode());
        assertEquals("비밀번호 수정에 성공했습니다.", responseEntity.getBody().getMessage());

    }

}