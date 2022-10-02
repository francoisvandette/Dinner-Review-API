package com.francoisvandette.DinnerReviewAPI.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long restaurantId;

    private String review;

    private Date dateSubmitted;

    private ApprovalStatus approvalStatus;

    private Double starRating;

    private Double peanutScore;

    private Double eggScore;

    private Double dairyScore;

}
