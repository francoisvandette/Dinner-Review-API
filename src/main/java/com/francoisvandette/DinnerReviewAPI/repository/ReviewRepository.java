package com.francoisvandette.DinnerReviewAPI.repository;

import com.francoisvandette.DinnerReviewAPI.model.ApprovalStatus;
import com.francoisvandette.DinnerReviewAPI.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    Iterable<Review> findAllByApprovalStatus(ApprovalStatus approvalStatus);
    Iterable<Review> findAllByRestaurantIdAndApprovalStatus(Long id, ApprovalStatus approvalStatus);
}
