package com.backend_api.review;

import com.backend_api.customer.Customer;
import com.backend_api.customer.CustomerService;
import com.backend_api.developer.DeveloperService;
import com.backend_api.games.Games;
import com.backend_api.games.GamesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ...existing imports...

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final GamesService gamesService;
    private final CustomerService customerService;
    private final DeveloperService developerService;

    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        Review created = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/reviews/{id}/developer-response")
    public ResponseEntity<Review> addDeveloperResponse(@PathVariable Long id, @RequestBody String response) {
        Review updated = reviewService.addDeveloperResponse(id, response);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/games/{gameId}/reviews")
    public ResponseEntity<?> createForGameApiPath(@PathVariable Long gameId,
                                                  @Valid @RequestBody Review review,
                                                  BindingResult br,
                                                  Principal principal) {
        if (br.hasErrors()) {
            log.error("Validation errors: {}", br.getAllErrors());
            return ResponseEntity.badRequest().body(br.getAllErrors());
        }

        if (principal == null) {
            log.error("Validation errors: {}", br.getAllErrors());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Games game = gamesService.getGamesById(gameId);
        
        if (game == null) {
            log.error("Game not found: {}", gameId);
            return ResponseEntity.notFound().build();
        }

        String username = principal.getName();
        Customer customer = customerService.getCustomerByUsername(username);
        if (customer == null) {
            log.error("Customer not found: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Customer not found");
        }

        review.setCustomer(customer);
        review.setGames(game);

        Review saved = reviewService.saveForGame(gameId, review, customer);
        log.info("Saved review: {}", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/games/{gameId}/reviews")
    public ResponseEntity<List<Review>> getReviewsForGame(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        if (game == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByGames(game));
    }
    
    @GetMapping("/reviews/games/{gameId}")
    public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        if (game == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByGames(game));
    }

    @GetMapping("/reviews/customer/{customerId}")
    public ResponseEntity<List<Review>> getCustomerReviews(@PathVariable Long customerId) {
        var customer = customerService.getCustomerById(customerId);
        if (customer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customer));
    }

    @GetMapping("/reviews/developer/{developerId}")
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