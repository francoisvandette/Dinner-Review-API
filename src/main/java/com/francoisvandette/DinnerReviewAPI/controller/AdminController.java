package com.francoisvandette.DinnerReviewAPI.controller;

import com.francoisvandette.DinnerReviewAPI.model.ApprovalStatus;
import com.francoisvandette.DinnerReviewAPI.model.Restaurant;
import com.francoisvandette.DinnerReviewAPI.model.Review;
import com.francoisvandette.DinnerReviewAPI.repository.RestaurantRepository;
import com.francoisvandette.DinnerReviewAPI.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminController(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/get-pending")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Review> getAllPendingReviews() {
        return this.reviewRepository.findAllByApprovalStatus(ApprovalStatus.UNDER_REVIEW);
    }

    @PutMapping("/update-status")
    @ResponseStatus(HttpStatus.OK)
    public Review updateReviewStatus(@RequestParam Long id, @RequestBody String newStatus) {

//        Admin admin = new Admin();
        Optional<Review> reviewOptional = this.reviewRepository.findById(id);
        if (reviewOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review could not be found.");
        }
        // saving the review to the DB
        Review review = reviewOptional.get();

        if (newStatus.equals("approved")) {
            review.setApprovalStatus(ApprovalStatus.APPROVED);
        } else if (newStatus.equals("denied")) {
            review.setApprovalStatus(ApprovalStatus.DENIED);
        } else {
            review.setApprovalStatus(ApprovalStatus.UNDER_REVIEW);
        }

        review = this.reviewRepository.save(review);

        // creating new scores
        // getting the restaurant and list of reviews for said restaurant
        Long restId = review.getRestaurantId();
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(restId);
        if (restaurantOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, restaurant could not be found.");
        }
        Restaurant restaurant = restaurantOptional.get();
        Iterable<Review> it = this.reviewRepository.findAllByRestaurantIdAndApprovalStatus(restId, ApprovalStatus.APPROVED);
        List<Review> list = new ArrayList<>();
        it.forEach(list::add);

        // rating
        double newStarRating = 0;
        double newPeanutScore = 0;
        double newEggScore = 0;
        double newDairyScore = 0;
        int length = list.size();
        int peanutLength = list.size();
        int eggLength = list.size();
        int dairyLength = list.size();

        for (Review i : list) {
            // totalling the Star Ratings
            newStarRating += i.getStarRating();

            // totalling the peanut scores and tracking the actual quantity of reviews that have that entered
            if (i.getPeanutScore() == null) {
                peanutLength--;
            } else {
                newPeanutScore += i.getPeanutScore();
            }

            // totalling the egg scores and tracking the actual quantity of reviews that have that entered
            if (i.getEggScore() == null) {
                eggLength--;
            } else {
                newEggScore += i.getEggScore();
            }

            // totalling the dairy scores and tracking the actual quantity of reviews that have that entered
            if (i.getDairyScore() == null) {
                dairyLength--;
            } else {
                newDairyScore += i.getDairyScore();
            }
        }

        // setting the decimal limit to 2
        final DecimalFormat df = new DecimalFormat("#.##");

        // averaging the rating and scores using their respective quantity
        newStarRating = Double.parseDouble(df.format(newStarRating / length));
        newPeanutScore = Double.parseDouble(df.format(newPeanutScore / peanutLength));
        newEggScore = Double.parseDouble(df.format(newEggScore / eggLength));
        newDairyScore = Double.parseDouble(df.format(newDairyScore / dairyLength));

        // setting the new rating and scores
        restaurant.setStarrating(newStarRating);
        restaurant.setPeanutscore(newPeanutScore);
        restaurant.setEggscore(newEggScore);
        restaurant.setDairyscore(newDairyScore);
        // saving the new restaurant rating and scores
        this.restaurantRepository.save(restaurant);

        // returning the original review
        return review;
    }
}
