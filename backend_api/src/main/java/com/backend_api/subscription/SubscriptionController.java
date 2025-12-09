package com.backend_api.subscription;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.Map;
import java.util.List;
import com.backend_api.customer.CustomerRepository;
import com.backend_api.customer.Customer;

@RestController
@RequestMapping("/api/customers/{customerId}/subscriptions")
public class SubscriptionController {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
    private final SubscriptionService subscriptionService;
    private final CustomerRepository customerRepository; 

    public SubscriptionController(SubscriptionService subscriptionService, CustomerRepository customerRepository) {
        this.subscriptionService = subscriptionService;
        this.customerRepository = customerRepository; 
    }

    @PostMapping
    public ResponseEntity<?> subscribe(
            @PathVariable("customerId") Long customerId,
            @RequestBody Map<String, Object> body,
            Principal principal) { 

        log.info("subscribe requested: pathCustomer={}, principal={}, body={}", customerId, principal != null ? principal.getName() : null, body);

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "not logged in"));
        }

        Customer sessionCustomer = customerRepository.findByEmail(principal.getName()).orElse(null);
        if (sessionCustomer == null || !sessionCustomer.getId().equals(customerId)) {
            return ResponseEntity.status(403).body(Map.of("error", "not authorized"));
        }

        Object g = body.get("gameId");
        if (g == null) return ResponseEntity.badRequest().body(Map.of("error", "gameId required"));
        Long gameId = Long.valueOf(String.valueOf(g));
        String plan = body.getOrDefault("planName", "basic").toString();

        boolean created = subscriptionService.addSubscription(customerId, gameId, plan);
        return ResponseEntity.status(created ? 201 : 200).body(Map.of("ok", true, "created", created, "gameId", gameId));
    }

    @DeleteMapping
    public ResponseEntity<?> unsubscribe(
            @PathVariable("customerId") Long customerId,
            @RequestBody(required = false) Map<String, Object> body,
            Principal principal) { 

        log.info("unsubscribe requested: pathCustomer={}, principal={}, body={}", customerId, principal != null ? principal.getName() : null, body);

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "not logged in"));
        }

        Customer sessionCustomer = customerRepository.findByEmail(principal.getName()).orElse(null);
        if (sessionCustomer == null || !sessionCustomer.getId().equals(customerId)) {
            return ResponseEntity.status(403).body(Map.of("error", "not authorized"));
        }

        Object g = body != null ? body.get("gameId") : null;
        if (g == null) return ResponseEntity.badRequest().body(Map.of("error", "gameId required"));
        Long gameId = Long.valueOf(String.valueOf(g));

        boolean removed = subscriptionService.removeSubscription(customerId, gameId);
        return ResponseEntity.ok(Map.of("ok", true, "removed", removed, "gameId", gameId));
    }

    @GetMapping
    public ResponseEntity<?> list(@PathVariable("customerId") Long customerId, Principal principal) {
        log.info("list subscriptions: pathCustomer={}, principal={}", customerId, principal != null ? principal.getName() : null);

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "not logged in"));
        }

        Customer sessionCustomer = customerRepository.findByEmail(principal.getName()).orElse(null);
        if (sessionCustomer == null || !sessionCustomer.getId().equals(customerId)) {
            return ResponseEntity.status(403).body(Map.of("error", "not authorized"));
        }

        List<Map<String, Object>> rows = subscriptionService.listSubscriptions(customerId);
        return ResponseEntity.ok(rows);
    }
}