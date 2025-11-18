package com.backend_api.subscription;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/subscriptions")
public class SubscriptionController {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(
            @PathVariable("customerId") Long customerId,
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        Object sess = session.getAttribute("customerId");
        log.info("subscribe requested: pathCustomer={}, sessionCustomer={}, body={}", customerId, sess, body);

        Long sessionCustomerId = null;
        try { sessionCustomerId = Long.valueOf(String.valueOf(sess)); } catch (Exception ignored) {}

        if (sessionCustomerId == null || !sessionCustomerId.equals(customerId)) {
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
            HttpSession session) {

        Object sess = session.getAttribute("customerId");
        log.info("unsubscribe requested: pathCustomer={}, sessionCustomer={}, body={}", customerId, sess, body);

        Long sessionCustomerId = null;
        try { sessionCustomerId = Long.valueOf(String.valueOf(sess)); } catch (Exception ignored) {}

        if (sessionCustomerId == null || !sessionCustomerId.equals(customerId)) {
            return ResponseEntity.status(403).body(Map.of("error", "not authorized"));
        }

        Object g = body != null ? body.get("gameId") : null;
        if (g == null) return ResponseEntity.badRequest().body(Map.of("error", "gameId required"));
        Long gameId = Long.valueOf(String.valueOf(g));

        boolean removed = subscriptionService.removeSubscription(customerId, gameId);
        return ResponseEntity.ok(Map.of("ok", true, "removed", removed, "gameId", gameId));
    }

    @GetMapping
    public ResponseEntity<?> list(@PathVariable("customerId") Long customerId, HttpSession session) {
        Object sess = session.getAttribute("customerId");
        log.info("list subscriptions: pathCustomer={}, sessionCustomer={}", customerId, sess);

        Long sessionCustomerId = null;
        try { sessionCustomerId = Long.valueOf(String.valueOf(sess)); } catch (Exception ignored) {}

        if (sessionCustomerId == null || !sessionCustomerId.equals(customerId)) {
            return ResponseEntity.status(403).body(Map.of("error", "not authorized"));
        }

        List<Map<String, Object>> rows = subscriptionService.listSubscriptions(customerId);
        return ResponseEntity.ok(rows);
    }
}