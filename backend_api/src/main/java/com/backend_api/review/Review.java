package com.backend_api.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import com.backend_api.customer.Customer;
import com.backend_api.games.Games;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonIgnoreProperties({"reviews", "subscriptions"})
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties("reviews")
    private Games games;

    public Games getGames() {
        return games;
    }

    public void setGames(Games game) {
        this.games = game;
    }

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

    public Review() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Integer getGraphicsRating() { return graphicsRating; }
    public void setGraphicsRating(Integer graphicsRating) { this.graphicsRating = graphicsRating; }

    public Integer getGameplayRating() { return gameplayRating; }
    public void setGameplayRating(Integer gameplayRating) { this.gameplayRating = gameplayRating; }

    public Integer getOverallRating() { return overallRating; }
    public void setOverallRating(Integer overallRating) { this.overallRating = overallRating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getDeveloperResponse() { return developerResponse; }
    public void setDeveloperResponse(String developerResponse) { this.developerResponse = developerResponse; }

    public LocalDateTime getDeveloperResponseDate() { return developerResponseDate; }
    public void setDeveloperResponseDate(LocalDateTime developerResponseDate) { this.developerResponseDate = developerResponseDate; }
}