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
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String city;

    private String province;

    private String postalcode;

    private Boolean interested_in_peanut_allergy;

    private Boolean interested_in_egg_allergy;

    private Boolean interested_in_dairy_allergy;


}
