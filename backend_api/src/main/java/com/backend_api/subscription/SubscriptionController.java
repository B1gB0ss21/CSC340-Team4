package com.backend_api.subscription;

import com.backend_api.customer.Customer;
import com.backend_api.customer.CustomerService;
import com.backend_api.developer.Developer;
import com.backend_api.developer.DeveloperService;
import com.backend_api.games.Games;
import com.backend_api.games.GamesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
        Subscription created = subscriptionService.createSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id,
                                                           @Valid @RequestBody Subscription subscriptionDetails) {
        Subscription updated = subscriptionService.updateSubscription(id, subscriptionDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Long id) {
        boolean ok = subscriptionService.cancelSubscription(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Subscription>> getCustomerSubscriptions(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(subscriptionService.getActiveSubscriptionsByCustomer(customer));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Subscription>> getGameSubscriptions(@PathVariable Long gameId) {
        Games game = gamesService.getGamesById(gameId);
        if (game == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByGames(game));
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<Subscription>> getDeveloperSubscriptions(@PathVariable Long developerId) {
        Developer developer = developerService.getDeveloperById(developerId);
        if (developer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByDeveloper(developer));
    }
}