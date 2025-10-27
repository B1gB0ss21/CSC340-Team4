package com.csc340.localharvest_hub.subscription;

import com.csc340.localharvest_hub.customer.CustomerService;
import com.csc340.localharvest_hub.farmer.DeveloperService;
import com.csc340.localharvest_hub.producebox.GamesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final GamesService gamesService;
    private final CustomerService customerService;
    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody Subscription subscription) {
        return ResponseEntity.ok(subscriptionService.createSubscription(subscription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id,
                                                           @Valid @RequestBody Subscription subscriptionDetails) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, subscriptionDetails));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Long id) {
        subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Subscription>> getCustomerSubscriptions(@PathVariable Long customerId) {
        return ResponseEntity.ok(
                subscriptionService.getActiveSubscriptionsByCustomer(
                        customerService.getCustomerById(customerId)
                )
        );
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Subscription>> getGameSubscriptions(@PathVariable Long gameId) {
        return ResponseEntity.ok(
                subscriptionService.getSubscriptionsByGames(
                        gamesService.getGamesById(gameId)
                )
        );
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<Subscription>> getDeveloperSubscriptions(@PathVariable Long developerId) {
        return ResponseEntity.ok(
                subscriptionService.getSubscriptionsByDeveloper(
                        developerService.getDeveloperById(developerId)
                )
        );
    }
}
