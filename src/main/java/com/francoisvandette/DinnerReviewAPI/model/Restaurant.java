package com.francoisvandette.DinnerReviewAPI.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    private String city;

    private String province;

    private String postalcode;

    private Double starrating;

    private Double peanutscore;

    private Double eggscore;

    private Double dairyscore;

}
