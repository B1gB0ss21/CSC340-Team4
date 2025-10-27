package com.csc340.localharvest_hub.farmer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public Developer createDeveloper(Developer developer) {
        if (developerRepository.existsByEmail(developer.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }
        return developerRepository.save(developer);
    }

    public Developer updateDeveloper(Long id, Developer developerDetails) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Developer not found"));

        developer.setName(developerDetails.getName());
        if (!developer.getEmail().equals(developerDetails.getEmail())
                && developerRepository.existsByEmail(developerDetails.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }
        developer.setEmail(developerDetails.getEmail());
        developer.setDob(developerDetails.getDob());

        return developerRepository.save(developer);
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Developer not found"));
    }

    public Developer getDeveloperByEmail(String email) {
        return developerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Developer not found"));
    }
}
