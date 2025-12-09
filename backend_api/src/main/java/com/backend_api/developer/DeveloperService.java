package com.backend_api.developer;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Transactional
public class DeveloperService {

    private static final Logger log = LoggerFactory.getLogger(DeveloperService.class);

    private final DeveloperRepository developerRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    // Create new profile 

    public Developer createDeveloper(Developer developer) {
        log.info("createDeveloper called for email={}", developer.getEmail());

        if (developer.getEmail() == null) {
            throw new IllegalArgumentException("email required");
        }

        if (developerRepository.existsByEmail(developer.getEmail())) {
            throw new IllegalStateException("email already registered");
        }

        if (developer.getPassword() != null) {
            developer.setPassword(encoder.encode(developer.getPassword()));
        }

        Developer saved = developerRepository.save(developer);
        log.info("developerRepository.save returned id={}", saved.getId());
        return saved;
    }

// authenticate 

public Developer authenticate(String email, String rawPassword) {
    if (email == null || rawPassword == null) return null;

    Optional<Developer> opt = developerRepository.findByEmail(email);
    if (opt.isEmpty()) return null;

    Developer d = opt.get();

    // password in DB is hashed; verify
    if (d.getPassword() == null) return null;

    boolean ok = encoder.matches(rawPassword, d.getPassword());
    return ok ? d : null;
}

// Update devolper info (name, info, dob, pass)

public Developer updateDeveloper(Long id, Developer developerDetails) {
    Optional<Developer> opt = developerRepository.findById(id);
    if (opt.isEmpty()) return null;

    Developer d = opt.get();

    if (developerDetails.getName() != null) {
        d.setName(developerDetails.getName());
    }
    if (developerDetails.getEmail() != null) {
        d.setEmail(developerDetails.getEmail());
    }
    if (developerDetails.getDob() != null) {
        d.setDob(developerDetails.getDob());
    }

    if (developerDetails.getPassword() != null && !developerDetails.getPassword().isBlank()) {
        d.setPassword(encoder.encode(developerDetails.getPassword()));
    }

    return developerRepository.save(d);
}

public Optional<Developer> findByEmail(String email) {
    return developerRepository.findByEmail(email);
}

public Developer getDeveloperById(Long id) {
    return developerRepository.findById(id).orElse(null);
}

public List<Developer> getAllDevelopers() {
    return developerRepository.findAll();
}

public List<Developer> searchByDob(String dob) {
    return developerRepository.findAll().stream()
            .filter(d -> d.getDob() != null && d.getDob().contains(dob))
            .collect(Collectors.toList());
}

// delete dev

public boolean deleteDeveloper(Long id) {
    if (!developerRepository.existsById(id)) return false;
    developerRepository.deleteById(id);
    return true;
}
}
