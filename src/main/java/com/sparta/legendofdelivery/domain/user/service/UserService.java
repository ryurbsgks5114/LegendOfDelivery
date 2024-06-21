package com.sparta.legendofdelivery.domain.user.service;

import com.sparta.legendofdelivery.domain.user.dto.UserLoginRequestDto;
import com.sparta.legendofdelivery.domain.user.dto.UserSignupRequestDto;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

        findByUserId(requestDto.getUserId()).ifPresent( (el) -> {
            throw new BadRequestException("이미 존재하는 아이디입니다.");
        });

        User user = new User(requestDto, UserRole.USER, UserStatus.NORMAL, UserOauth.OUR);

        String encryptionPassword = passwordEncoder.encode(requestDto.getPassword());
        user.encryptionPassword(encryptionPassword);

        userRepository.save(user);

    }

    public void login(UserLoginRequestDto requestDto) {

        Optional<User> user = findByUserId(requestDto.getUserId());

        if (user.isEmpty() || user.get().getStatus().equals(UserStatus.LEAVE) || !checkPassword(requestDto.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("아이디, 비밀번호를 확인해주세요.");
        }

    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    private boolean checkPassword(String requestPassword, String userPassword) {
        return passwordEncoder.matches(requestPassword, userPassword);
    }

}
