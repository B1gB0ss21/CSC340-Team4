package com.csc340.localharvest_hub.review;

import com.csc340.localharvest_hub.customer.CustomerService;
import com.csc340.localharvest_hub.farmer.DeveloperService;
import com.csc340.localharvest_hub.producebox.Games;
import com.csc340.localharvest_hub.producebox.GamesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final GamesService gamesService;
    private final CustomerService customerService;
    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PostMapping("/{id}/developer-response")
    public ResponseEntity<Review> addDeveloperResponse(@PathVariable Long id, @RequestBody String response) {
        return ResponseEntity.ok(reviewService.addDeveloperResponse(id, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
        return ResponseEntity.ok(reviewService.getReviewsByGames(gamesService.getGamesById(gameId)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getCustomerReviews(@PathVariable Long customerId) {
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customerService.getCustomerById(customerId)));
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<Review>> getDeveloperReviews(@PathVariable Long developerId) {
        return ResponseEntity.ok(reviewService.getReviewsByDeveloper(developerService.getDeveloperById(developerId)));
    }

    @GetMapping("/games/{gameId}/ratings")
    public ResponseEntity<Map<String, Double>> getGamesRatings(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        Map<String, Double> ratings = new HashMap<>();
        ratings.put("Overall", reviewService.getAverageOverallRating(game));
        ratings.put("Graphics", reviewService.getAverageGraphicsRating(game));
        ratings.put("Gameplay", reviewService.getAverageGameplayRating(game));
        return ResponseEntity.ok(ratings);
    }
}
