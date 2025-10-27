package com.csc340.localharvest_hub.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import com.csc340.localharvest_hub.customer.Customer;
import com.csc340.localharvest_hub.producebox.Games;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"reviews", "subscriptions"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties("reviews")
    private Games games;

    @NotNull @Min(1) @Max(5)
    private Double graphicsRating;

    @NotNull @Min(1) @Max(5)
    private Double gameplayRating;

    @Min(1) @Max(5)
    private Double overallRating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String developerResponse;

    private LocalDateTime developerResponseDate;
}
