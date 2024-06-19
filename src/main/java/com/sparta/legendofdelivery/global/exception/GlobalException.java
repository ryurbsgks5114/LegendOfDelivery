package com.sparta.legendofdelivery.global.exception;

import com.sparta.legendofdelivery.global.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "Global Exception")
@RestControllerAdvice
@RestController
public class GlobalException {

    private ResponseEntity<CommonResponse<Void>> createResponseEntity(Exception e, HttpStatus httpStatusCode) {

        CommonResponse<Void> response = new CommonResponse<>(e.getMessage());

        log.error("Exception: {}", e.getMessage());

        return ResponseEntity.status(httpStatusCode).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<Void>> badRequestException(BadRequestException e) {
        return createResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> unauthorizedException(UnauthorizedException e) {
        return createResponseEntity(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> notFoundException(NotFoundException e) {
        return createResponseEntity(e, HttpStatus.NOT_FOUND);
    }

}
