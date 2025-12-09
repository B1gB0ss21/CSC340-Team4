package com.backend_api.developer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend_api.customer.CustomerService;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/devolopers")


public class DeveloperController {

    private final DeveloperService developerService;

     public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;

     }


      @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            if (email == null || email.isBlank()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "email is required"));
            }

            Optional<Developer> maybe = developerService.findByEmail(email);

            if (maybe.isPresent()) {
                // UPDATE existing developer
                Developer existing = maybe.get();

                existing.setName((String) body.getOrDefault("name", existing.getName()));

                String dob = (String) body.getOrDefault("dob",
                               body.getOrDefault("dateOfBirth", existing.getDob()));
                existing.setDob(dob);

                String pw = (String) body.get("password");
                if (pw != null && !pw.isBlank()) {
                    existing.setPassword(pw);
                }

                Developer updated = developerService.updateDeveloper(existing.getId(), existing);

                Map<String, Object> outUpd = Map.of(
                        "id", updated.getId(),
                        "name", updated.getName(),
                        "email", updated.getEmail(),
                        "dob", updated.getDob()
                );

                return ResponseEntity.ok(outUpd);

            } else {
                // CREATE new developer
                Developer d = new Developer();
                d.setName((String) body.getOrDefault("name", ""));
                d.setEmail(email);

                String dob = (String) body.getOrDefault("dob",
                               body.getOrDefault("dateOfBirth", null));
                d.setDob(dob);

                d.setPassword((String) body.get("password"));

                Developer saved = developerService.createDeveloper(d);

                Map<String, Object> out = Map.of(
                        "id", saved.getId(),
                        "name", saved.getName(),
                        "email", saved.getEmail(),
                        "dob", saved.getDob()
                );

                return ResponseEntity.status(HttpStatus.CREATED).body(out);
            }

        } catch (IllegalStateException | DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body, HttpSession session) {

        String email = (String) body.get("email");
        String password = (String) body.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "email and password required"));
        }

        Developer dev = developerService.authenticate(email, password);
        if (dev == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "invalid credentials"));
        }

        session.setAttribute("developerId", dev.getId());

        Map<String, Object> dto = Map.of(
                "id", dev.getId(),
                "name", dev.getName(),
                "email", dev.getEmail(),
                "dob", dev.getDob()
        );

        return ResponseEntity.ok(dto);
    }

   
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "logged out"));
    }

   
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentDeveloper(HttpSession session) {
        Object idObj = session.getAttribute("developerId");
        if (idObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "not logged in"));
        }

        Long id = (Long) idObj;
        Developer dev;
        try {
            dev = developerService.getDeveloperById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "developer not found"));
        }

        Map<String, Object> dto = Map.of(
                  "id", dev.getId(),
                "name", dev.getName(),
                "email", dev.getEmail(),
                "dob", dev.getDob()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloper(@PathVariable Long id) {
        try {
            Developer dev = developerService.getDeveloperById(id);
            return ResponseEntity.ok(dev);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable Long id,
                                             @Valid @RequestBody Developer developerDetails) {
        try {
            Developer updated = developerService.updateDeveloper(id, developerDetails);
            if (updated == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            // email already registered, etc.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        boolean deleted = developerService.deleteDeveloper(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Developer>> listDevelopers() {
        return ResponseEntity.ok(developerService.getAllDevelopers());
    }
}
