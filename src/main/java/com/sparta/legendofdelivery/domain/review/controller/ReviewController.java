package com.sparta.legendofdelivery.domain.review.controller;


import com.sparta.legendofdelivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

}
