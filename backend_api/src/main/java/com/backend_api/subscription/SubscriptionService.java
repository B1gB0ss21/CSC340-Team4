package com.backend_api.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.backend_api.subscription.Subscription;
import com.backend_api.subscription.SubscriptionRepository;

@Service
public class SubscriptionService {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private final JdbcTemplate jdbc;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(JdbcTemplate jdbc, SubscriptionRepository subscriptionRepository) {
        this.jdbc = jdbc;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public Subscription create(Subscription s) {
        try {
            Subscription saved = subscriptionRepository.save(s);
            log.info("Saved subscription id={} email={} to DB", saved.getId(), saved.getEmail());
            return saved;
        } catch (Exception ex) {
            log.error("Failed to save subscription", ex);
            throw ex;
        }
    }

    public List<Subscription> listAll() {
        return subscriptionRepository.findAll();
    }

    public boolean delete(Long id) {
        if (!subscriptionRepository.existsById(id)) return false;
        subscriptionRepository.deleteById(id);
        return true;
    }

    public boolean addSubscription(Long customerId, Long gameId, String planName) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(gameId);
        try {
            int rows = jdbc.update(
                "INSERT INTO subscriptions (active, start_date, end_date, type, customer_id, game_id, game_name, game_ref_id, plan_name, created_at) " +
                "VALUES (?, current_date, NULL, ?, ?, ?, (SELECT name FROM games WHERE id = ?), ?, ?, now()) " +
                "ON CONFLICT (customer_id, game_id) DO NOTHING",
                true,
                "MONTHLY",
                customerId,
                gameId,
                gameId,
                null,
                planName != null ? planName : "basic"
            );
            log.info("addSubscription result rows={} for customerId={} gameId={}", rows, customerId, gameId);
            return rows > 0;
        } catch (Exception ex) {
            log.error("addSubscription failed for customerId={} gameId={} plan={} - error:", customerId, gameId, planName, ex);
            throw ex;
        }
    }

    public boolean removeSubscription(Long customerId, Long gameId) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(gameId);
        try {
            int rows = jdbc.update(
                "DELETE FROM subscriptions WHERE customer_id = ? AND game_id = ?",
                customerId, gameId
            );
            log.info("removeSubscription rows={} for customerId={} gameId={}", rows, customerId, gameId);
            return rows > 0;
        } catch (Exception ex) {
            log.error("removeSubscription failed for customerId={} gameId={} - error:", customerId, gameId, ex);
            throw ex;
        }
    }

    public boolean exists(Long customerId, Long gameId) {
        try {
            Integer count = jdbc.queryForObject(
                "SELECT count(1) FROM subscriptions WHERE customer_id = ? AND game_id = ?",
                Integer.class, customerId, gameId
            );
            return count != null && count > 0;
        } catch (Exception ex) {
            log.error("exists check failed for customerId={} gameId={} - error:", customerId, gameId, ex);
            throw ex;
        }
    }

    public List<Map<String, Object>> listSubscriptions(Long customerId) {
        try {
            return jdbc.queryForList(
                "SELECT id, active, start_date, end_date, type, customer_id, game_id, game_name, game_ref_id, plan_name, created_at " +
                "FROM subscriptions WHERE customer_id = ? ORDER BY created_at DESC",
                customerId
            );
        } catch (Exception ex) {
            log.error("listSubscriptions failed for customerId={} - error:", customerId, ex);
            throw ex;
        }
    }
}