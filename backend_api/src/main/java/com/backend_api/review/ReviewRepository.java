package com.backend_api.review;

import com.backend_api.customer.Customer;
import com.backend_api.developer.Developer;
import com.backend_api.games.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGames(Games games);
    List<Review> findByCustomer(Customer customer);
    List<Review> findByGamesIdOrderByCreatedAtDesc(Long gameId);
    
    @Query("select r from Review r join r.games g where g.developer = :developer")
    List<Review> findByGamesDeveloper(@Param("developer") Developer developer);
}