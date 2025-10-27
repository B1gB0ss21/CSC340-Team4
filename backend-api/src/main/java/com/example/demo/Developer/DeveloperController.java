package com.csc340.localharvest_hub.farmer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@Valid @RequestBody Developer developer) {
        return ResponseEntity.ok(developerService.createDeveloper(developer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable Long id,
                                                     @Valid @RequestBody Developer developerDetails) {
        return ResponseEntity.ok(developerService.updateDeveloper(id, developerDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.getDeveloperById(id));
    }
}
