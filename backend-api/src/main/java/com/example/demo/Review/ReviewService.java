package com.csc340.localharvest_hub.review;

import com.csc340.localharvest_hub.customer.Customer;
import com.csc340.localharvest_hub.farmer.Developer;
import com.csc340.localharvest_hub.producebox.Games;

import jakarta.persistence.EntityNotFoundException;
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

    public double getAverageOverallRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getOverallRating() != null ? r.getOverallRating() : 0.0)
                .average();
        return average.orElse(0.0);
    }

    public double getAverageGraphicsRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getGraphicsRating() != null ? r.getGraphicsRating() : 0.0)
                .average();
        return average.orElse(0.0);
    }

    public double getAverageGameplayRating(Games games) {
        List<Review> reviews = reviewRepository.findByGames(games);
        OptionalDouble average = reviews.stream()
                .mapToDouble(r -> r.getGameplayRating() != null ? r.getGameplayRating() : 0.0)
                .average();
        return average.orElse(0.0);
    }

    public Review createReview(Review review) {
        double graphicsRating = review.getGraphicsRating() != null ? review.getGraphicsRating() : 0;
        double gameplayRating = review.getGameplayRating() != null ? review.getGameplayRating() : 0;
        review.setOverallRating((graphicsRating + gameplayRating) / 2.0);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public Review addDeveloperResponse(Long id, String response) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setDeveloperResponse(response);
        review.setDeveloperResponseDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review not found");
        }
        reviewRepository.deleteById(id);
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
}
