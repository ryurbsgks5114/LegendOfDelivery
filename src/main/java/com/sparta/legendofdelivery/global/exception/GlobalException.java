package com.sparta.legendofdelivery.global.exception;

import com.sparta.legendofdelivery.global.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "Global Exception")
@RestControllerAdvice
@RestController
public class GlobalException {

    private ResponseEntity<CommonResponse<Void>> createResponseEntity(Exception e, HttpStatus httpStatusCode) {

        CommonResponse<Void> response = new CommonResponse<>(e.getMessage());

        log.error(e.getMessage());

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> notValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError el : e.getBindingResult().getFieldErrors()) {
            errors.put(el.getField(), el.getDefaultMessage());
        }

        log.error("입력값을 확인해 주세요.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
