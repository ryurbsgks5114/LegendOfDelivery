package com.sparta.legendofdelivery.domain.store.controller;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<Store>> store(@Valid @RequestBody StoreRequestDto requestDto) {

        DataResponse<Store> response = storeService.createStore(requestDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<Store>> getStores(@PathVariable Long id) {

        DataResponse<Store> response = storeService.getStoreById(id);

        return  ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<Store>> updateStore(@PathVariable Long id, @Valid @RequestBody StoreRequestDto requestDto) {

        DataResponse<Store> response = storeService.updateStore(id, requestDto);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteStore(@PathVariable Long id) {

        return ResponseEntity.ok(storeService.deleteStore(id));

    }

}
