package com.backend_api.games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    List<Games> findByAvailable(boolean available);
    List<Games> findByNameContainingIgnoreCase(String name);
    List<Games> findByPriceLessThanEqual(Double price);
    List<Games> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, Double price);
}