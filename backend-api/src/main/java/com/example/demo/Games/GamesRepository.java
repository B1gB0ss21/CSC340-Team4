package com.csc340.localharvest_hub.producebox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    List<Games> findByAvailable(boolean available);
}
