package com.backend_api.review;

import com.backend_api.customer.Customer;
import com.backend_api.developer.Developer;
import com.backend_api.games.Games;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Double getAverageOverallRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        if (reviews == null || reviews.isEmpty()) return null;
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getOverallRating() != null ? r.getOverallRating() : 0.0)
                .average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public Double getAverageGraphicsRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        if (reviews == null || reviews.isEmpty()) return null;
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getGraphicsRating() != null ? r.getGraphicsRating() : 0.0)
                .average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public Double getAverageGameplayRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        if (reviews == null || reviews.isEmpty()) return null;
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getGameplayRating() != null ? r.getGameplayRating() : 0.0)
                .average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public Review createReview(Review review) {
        double graphicsRating = review.getGraphicsRating() != null ? review.getGraphicsRating() : 0;
        double gameplayRating = review.getGameplayRating() != null ? review.getGameplayRating() : 0;

        int overall = (int) Math.round((graphicsRating + gameplayRating) / 2.0);
        review.setOverallRating(overall);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public Review addDeveloperResponse(Long id, String response) {
        return reviewRepository.findById(id).map(r -> {
            r.setDeveloperResponse(response);
            r.setDeveloperResponseDate(LocalDateTime.now());
            return reviewRepository.save(r);
        }).orElse(null);
    }

    public Review createReviewInCode(Long gameId, Long customerId, int graphicsRating, int gameplayRating, String comment) {
    Games game = new Games();
    game.setId(gameId);

    Customer customer = new Customer();
    customer.setId(customerId);

    Review review = new Review();
    review.setGames(game);
    review.setCustomer(customer);
    review.setGraphicsRating(graphicsRating);
    review.setGameplayRating(gameplayRating);
    review.setComment(comment);
    review.setCreatedAt(LocalDateTime.now());

    // Calculate overall rating
    int overall = (int) Math.round((graphicsRating + gameplayRating) / 2.0);
    review.setOverallRating(overall);

    return reviewRepository.save(review);
}
    
    public boolean deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            return false;
        }
        reviewRepository.deleteById(id);
        return true;
    }

    public List<Review> getReviewsByGames(Games games) {
        return reviewRepository.findByGames(games);
    }

    public List<Review> getReviewsByCustomer(Customer customer) {
        return reviewRepository.findByCustomer(customer);
    }

    public List<Review> getReviewsByDeveloper(Developer developer) {
        return reviewRepository.findByGamesDeveloper(developer);
    }

    public List<Review> findByGameId(Long gameId) {
        return reviewRepository.findByGamesIdOrderByCreatedAtDesc(gameId);
    }

    @Transactional
    public Review saveForGame(Long gameId, Review review, Customer customer) {
    Games g = new Games();
    g.setId(gameId);
    review.setGames(g);
    review.setCustomer(customer); 

    if (review.getCreatedAt() == null) review.setCreatedAt(LocalDateTime.now());
        double graphicsRating = review.getGraphicsRating() != null ? review.getGraphicsRating() : 0;
        double gameplayRating = review.getGameplayRating() != null ? review.getGameplayRating() : 0;
        int overall = (int) Math.round((graphicsRating + gameplayRating) / 2.0);
        review.setOverallRating(overall);
    return reviewRepository.save(review);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return deleteReview(id);
    }
}