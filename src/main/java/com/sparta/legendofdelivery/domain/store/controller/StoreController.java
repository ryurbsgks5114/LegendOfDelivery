package com.sparta.legendofdelivery.domain.store.controller;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<DataResponse> store(@Valid @RequestBody StoreRequestDto requestDto) {

        DataResponse<Store> response = storeService.createStore(requestDto);

        return ResponseEntity.ok(response);
    }

}
