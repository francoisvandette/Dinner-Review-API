package com.francoisvandette.DinnerReviewAPI.controller;

import com.francoisvandette.DinnerReviewAPI.model.ApprovalStatus;
import com.francoisvandette.DinnerReviewAPI.model.Member;
import com.francoisvandette.DinnerReviewAPI.model.Review;
import com.francoisvandette.DinnerReviewAPI.repository.MemberRepository;
import com.francoisvandette.DinnerReviewAPI.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;


    public ReviewController(ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public Review submitReview(@RequestBody Member member, @RequestBody Review review) {
        Optional<Member> userOptional = this.memberRepository.findByUsername(member.getUsername());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member does not exist.");
        }
        return this.reviewRepository.save(review);
    }

    @GetMapping("/restaurant")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Review> getAllApprovedReviewsForRestaurant(@RequestParam Long id) {
        return this.reviewRepository.findAllByRestaurantIdAndApprovalStatus(id, ApprovalStatus.APPROVED);
    }

}
