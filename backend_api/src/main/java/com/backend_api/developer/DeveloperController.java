package com.backend_api.developer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@Valid @RequestBody Developer developer) {
        Developer created = developerService.createDeveloper(developer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable Long id,
                                                     @Valid @RequestBody Developer developerDetails) {
        Developer updated = developerService.updateDeveloper(id, developerDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable Long id) {
        Developer dev = developerService.getDeveloperById(id);
        if (dev == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dev);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        boolean deleted = developerService.deleteDeveloper(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}