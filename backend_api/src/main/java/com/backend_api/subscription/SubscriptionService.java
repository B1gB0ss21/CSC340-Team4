package com.backend_api.subscription;

import com.backend_api.customer.Customer;
import com.backend_api.developer.Developer;
import com.backend_api.games.Games;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

   
    public Subscription updateSubscription(Long id, Subscription subscriptionDetails) {
        Optional<Subscription> opt = subscriptionRepository.findById(id);
        if (opt.isEmpty()) return null;

        Subscription subscription = opt.get();
        subscription.setType(subscriptionDetails.getType());
        subscription.setActive(subscriptionDetails.isActive());
        subscription.setStartDate(subscriptionDetails.getStartDate());
        subscription.setEndDate(subscriptionDetails.getEndDate());

        return subscriptionRepository.save(subscription);
    }

    
    public boolean cancelSubscription(Long id) {
        Optional<Subscription> opt = subscriptionRepository.findById(id);
        if (opt.isEmpty()) return false;
        Subscription subscription = opt.get();
        subscription.setActive(false);
        subscriptionRepository.save(subscription);
        return true;
    }

    public List<Subscription> getActiveSubscriptionsByCustomer(Customer customer) {
        return subscriptionRepository.findByCustomerAndActive(customer, true);
    }

    public List<Subscription> getSubscriptionsByGames(Games games) {
        return subscriptionRepository.findByGames(games);
    }

    public List<Subscription> getSubscriptionsByDeveloper(Developer developer) {
        return subscriptionRepository.findByGamesDeveloper(developer);
    }
}