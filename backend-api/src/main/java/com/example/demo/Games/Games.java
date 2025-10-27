package com.csc340.localharvest_hub.producebox;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.csc340.localharvest_hub.review.Review;
import com.csc340.localharvest_hub.subscription.Subscription;

@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available = true;

    // mappedBy should match the field name in Subscription (private Games games;)
    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("games")
    private List<Subscription> subscriptions = new ArrayList<>();

    // mappedBy should match the field name in Review (private Games games;)
    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("games")
    private List<Review> reviews = new ArrayList<>();
}
