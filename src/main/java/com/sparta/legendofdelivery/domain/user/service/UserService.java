package com.sparta.legendofdelivery.domain.user.service;

import com.sparta.legendofdelivery.domain.user.dto.UserSignupRequestDto;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import com.sparta.legendofdelivery.global.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    public void logout() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findByUserId(userDetails.getUsername()).orElseThrow( () -> new NotFoundException("해당 회원은 존재하지 않습니다."));

        user.updateRefreshToken(null);

    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

}
