package com.francoisvandette.DinnerReviewAPI.repository;

import com.francoisvandette.DinnerReviewAPI.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Optional<Restaurant> findByNameAndPostalcode(String name, String postalCode);

//    @Query(value = "SELECT * FROM RESTAURANT WHERE postalcode = ?1 AND (peanutscore IS NOT NULL OR eggscore IS NOT NULL OR dairyscore IS NOT NULL) ORDER BY name DESC", nativeQuery = true)
//    Iterable<Restaurant> findAllByPostalcodeAndAnyAllergies(String postalCode);

    Iterable<Restaurant> findAllByPostalcodeAndPeanutscoreIsNotNull(String postalCode);
    Iterable<Restaurant> findAllByPostalcodeAndEggscoreIsNotNull(String postalCode);
    Iterable<Restaurant> findAllByPostalcodeAndDairyscoreIsNotNull(String postalCode);

}
