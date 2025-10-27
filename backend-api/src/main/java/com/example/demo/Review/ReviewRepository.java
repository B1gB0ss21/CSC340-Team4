package com.csc340.localharvest_hub.review;

import com.csc340.localharvest_hub.customer.Customer;
import com.csc340.localharvest_hub.farmer.Developer;
import com.csc340.localharvest_hub.producebox.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGames(Games games);
    List<Review> findByCustomer(Customer customer);
    List<Review> findByGamesDeveloper(Developer developer);
}
