package com.backend_api.review;

import com.backend_api.customer.CustomerService;
import com.backend_api.developer.DeveloperService;
import com.backend_api.games.Games;
import com.backend_api.games.GamesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
        Review created = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/developer-response")
    public ResponseEntity<Review> addDeveloperResponse(@PathVariable Long id, @RequestBody String response) {
        Review updated = reviewService.addDeveloperResponse(id, response);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        if (game == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByGames(game));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getCustomerReviews(@PathVariable Long customerId) {
        var customer = customerService.getCustomerById(customerId);
        if (customer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customer));
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<Review>> getDeveloperReviews(@PathVariable Long developerId) {
        var developer = developerService.getDeveloperById(developerId);
        if (developer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByDeveloper(developer));
    }

    @GetMapping("/games/{gameId}/ratings")
    public ResponseEntity<Map<String, Double>> getGamesRatings(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        if (game == null) return ResponseEntity.notFound().build();
        Map<String, Double> ratings = new HashMap<>();
        ratings.put("Overall", reviewService.getAverageOverallRating(game));
        ratings.put("Graphics", reviewService.getAverageGraphicsRating(game));
        ratings.put("Gameplay", reviewService.getAverageGameplayRating(game));
        return ResponseEntity.ok(ratings);
    }
}
