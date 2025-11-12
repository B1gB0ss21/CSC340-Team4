package com.backend_api.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import com.backend_api.customer.Customer;
import com.backend_api.games.Games;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"reviews", "subscriptions"})
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties("reviews")
    private Games games;

    @NotNull @Min(1) @Max(5)
    private Integer graphicsRating;

    @NotNull @Min(1) @Max(5)
    private Integer gameplayRating;

    @Min(1) @Max(5)
    private Integer overallRating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String developerResponse;

    private LocalDateTime developerResponseDate;
}