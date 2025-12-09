package com.backend_api.customer;
import com.backend_api.games.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByDateOfBirthContaining(String dob);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
}