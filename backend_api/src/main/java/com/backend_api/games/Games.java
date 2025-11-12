package com.backend_api.games;

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

import com.backend_api.review.Review;
import com.backend_api.subscription.Subscription;
import com.backend_api.developer.Developer;

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
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    @JsonIgnoreProperties("games")
    private Developer developer;

    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("games")
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "games", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("games")
    private List<Review> reviews = new ArrayList<>();
}