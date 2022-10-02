package com.francoisvandette.DinnerReviewAPI.controller;

import com.francoisvandette.DinnerReviewAPI.model.Restaurant;
import com.francoisvandette.DinnerReviewAPI.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) { this.restaurantRepository = restaurantRepository; };

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@RequestBody Restaurant newRestaurant) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findByNameAndPostalcode(newRestaurant.getName(), newRestaurant.getPostalcode());
        if (restaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant already exist.");
        }
        return this.restaurantRepository.save(newRestaurant);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Restaurant> getAllRestaurants() {
        return this.restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant getRestaurant(@PathVariable(name="id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found.");
        }
        return restaurantOptional.get();
    }

//    @GetMapping("/search-all-allergies")
//    @ResponseStatus(HttpStatus.OK)
//    public Iterable<Restaurant> getRestaurantsByPostalCodeAndAllergies(@RequestParam String postalCode) {
//        return this.restaurantRepository.findAllByPostalcodeAndAnyAllergies(postalCode);
//    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Restaurant> getRestaurantsByPostalCodeAndAllergy(@RequestParam String postalcode, @RequestParam String allergy) {
        if (allergy.equalsIgnoreCase("peanut")) {
            return this.restaurantRepository.findAllByPostalcodeAndPeanutscoreIsNotNull(postalcode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            return this.restaurantRepository.findAllByPostalcodeAndEggscoreIsNotNull(postalcode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            return this.restaurantRepository.findAllByPostalcodeAndDairyscoreIsNotNull(postalcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Allergy not supported, must be: 'peanut', 'egg', or 'dairy'.");
        }
    }


}
