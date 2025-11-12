package com.backend_api.subscription;

import com.backend_api.customer.Customer;
import com.backend_api.developer.Developer;
import com.backend_api.games.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByCustomerAndActive(Customer customer, boolean active);
    List<Subscription> findByGames(Games games);

    @Query("select s from Subscription s where s.games.developer = :developer")
    List<Subscription> findByGamesDeveloper(@Param("developer") Developer developer);
}