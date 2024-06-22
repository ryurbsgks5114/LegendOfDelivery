package com.sparta.legendofdelivery.domain.dibs.controller;

import com.sparta.legendofdelivery.domain.dibs.service.DibsService;
import com.sparta.legendofdelivery.domain.user.security.UserDetailsImpl;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dibs")
public class DibsController {

    private final DibsService dibsService;

    public DibsController(DibsService dibsService) {
        this.dibsService = dibsService;
    }

    @PostMapping("{storeId}")
    public ResponseEntity<MessageResponse> addDibs(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(dibsService.addDibs(storeId, userDetails.getUser()));

    }

    @DeleteMapping("{dibsId}")
    public ResponseEntity<MessageResponse> deleteDibs(@PathVariable Long dibsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(dibsService.deleteDibs(dibsId, userDetails.getUser()));

    }
}
