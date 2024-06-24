package com.sparta.legendofdelivery.domain.like.controller;

import com.sparta.legendofdelivery.domain.like.service.LikeService;
import com.sparta.legendofdelivery.domain.user.security.UserDetailsImpl;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<MessageResponse> addLike(@PathVariable Long reviewId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(likeService.addLike(reviewId, userDetails.getUser()));

    }

    @DeleteMapping("/reviews/{reviewId}/like")
    public ResponseEntity<MessageResponse> deleteLike(@PathVariable Long reviewId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(likeService.deleteLike(reviewId, userDetails.getUser()));

    }
}
