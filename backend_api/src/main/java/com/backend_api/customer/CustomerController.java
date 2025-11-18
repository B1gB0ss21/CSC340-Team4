package com.backend_api.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.security.Principal;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            if (email == null || email.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "email is required"));
            }

            Optional<Customer> maybe = customerService.findByEmail(email);
            if (maybe.isPresent()) {
                Customer existing = maybe.get();
                existing.setName((String) body.getOrDefault("name", existing.getName()));
                existing.setDateOfBirth((String) body.getOrDefault("dateOfBirth", existing.getDateOfBirth()));
                existing.setFavoriteGenre((String) body.getOrDefault("favoriteGenre", existing.getFavoriteGenre()));
                String pw = (String) body.get("password");
                if (pw != null && !pw.isBlank()) {
                    existing.setPassword(pw);
                }
                Customer updated = customerService.updateCustomer(existing.getId(), existing);
                Map<String,Object> outUpd = Map.of(
                    "id", updated.getId(),
                    "name", updated.getName(),
                    "email", updated.getEmail(),
                    "dateOfBirth", updated.getDateOfBirth(),
                    "favoriteGenre", updated.getFavoriteGenre(),
                    "createdAt", updated.getCreatedAt()
                );
                return ResponseEntity.ok(outUpd);
            } else {
                Customer c = new Customer();
                c.setName((String) body.getOrDefault("name", ""));
                c.setDateOfBirth((String) body.getOrDefault("dateOfBirth", body.getOrDefault("dob", null)));
                c.setFavoriteGenre((String) body.getOrDefault("favoriteGenre", body.getOrDefault("genre", null)));
                c.setEmail(email);
                c.setPassword((String) body.get("password"));
                Customer saved = customerService.createCustomer(c);
                Map<String,Object> out = Map.of(
                    "id", saved.getId(),
                    "name", saved.getName(),
                    "email", saved.getEmail(),
                    "dateOfBirth", saved.getDateOfBirth(),
                    "favoriteGenre", saved.getFavoriteGenre(),
                    "createdAt", saved.getCreatedAt()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(out);
            }
        } catch (IllegalStateException | DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body, HttpSession session) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }

        Customer user = customerService.authenticate(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "invalid credentials"));
        }

        session.setAttribute("customerId", user.getId());

        Map<String,Object> userDto = Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "dateOfBirth", user.getDateOfBirth(),
            "favoriteGenre", user.getFavoriteGenre(),
            "createdAt", user.getCreatedAt()
        );

        return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(), "user", userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Customer c = customerService.getCustomerById(id);
        if (c == null) return ResponseEntity.notFound().build();
        Map<String,Object> out = Map.of(
            "id", c.getId(),
            "name", c.getName(),
            "email", c.getEmail(),
            "dateOfBirth", c.getDateOfBirth(),
            "favoriteGenre", c.getFavoriteGenre(),
            "createdAt", c.getCreatedAt()
        );
        return ResponseEntity.ok(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updated = customerService.updateCustomer(id, customerDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        Map<String,Object> out = Map.of(
            "id", updated.getId(),
            "name", updated.getName(),
            "email", updated.getEmail(),
            "dateOfBirth", updated.getDateOfBirth(),
            "favoriteGenre", updated.getFavoriteGenre(),
            "createdAt", updated.getCreatedAt()
        );
        return ResponseEntity.ok(out);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> list() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/me")
    public ResponseEntity<?> editMe(@RequestBody Map<String,Object> body, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String email = principal.getName();
        Optional<Customer> maybe = customerService.findByEmail(email);
        if (maybe.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Customer existing = maybe.get();
        existing.setName((String) body.getOrDefault("name", existing.getName()));
        existing.setDateOfBirth((String) body.getOrDefault("dateOfBirth", existing.getDateOfBirth()));
        existing.setFavoriteGenre((String) body.getOrDefault("favoriteGenre", existing.getFavoriteGenre()));
        String pw = (String) body.get("password");
        if (pw != null && !pw.isBlank()) existing.setPassword(pw);
        Customer updated = customerService.updateCustomer(existing.getId(), existing);
        return ResponseEntity.ok(Map.of(
            "id", updated.getId(),
            "email", updated.getEmail(),
            "name", updated.getName()
        ));
    }
}