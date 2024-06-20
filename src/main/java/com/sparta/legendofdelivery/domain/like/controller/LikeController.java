package com.sparta.legendofdelivery.domain.like.controller;

import com.sparta.legendofdelivery.domain.like.service.LikeService;
import com.sparta.legendofdelivery.global.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

//    @PostMapping("{reviewId}")
//    public ResponseEntity<CommonResponse<Void>> addLike(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//    }
}
