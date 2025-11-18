package com.backend_api;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.backend_api.customer.Customer;
import com.backend_api.customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object raw = session.getAttribute("customerId");
        log.debug("/api/me sessionId={} attr={}", session.getId(), raw);
        if (raw == null) return ResponseEntity.ok(Map.of());

        Long id;
        try { id = Long.valueOf(String.valueOf(raw)); } catch (Exception e) { return ResponseEntity.ok(Map.of("id", raw)); }

        Customer c = customerService.getCustomerById(id);
        if (c == null) return ResponseEntity.ok(Map.of("id", id));

        Map<String,Object> out = new HashMap<>();
        out.put("id", c.getId());
        out.put("email", safeGet(c, "email", "getEmail"));

        String name = safeGet(c, "name", "getName", "getFullName", "getUsername", "firstName", "getFirstName");
        if (name != null) out.put("name", name);

        String dob = safeGet(c, "dateOfBirth", "getDateOfBirth", "dob", "getDob", "birthDate", "getBirthDate");
        if (dob != null) out.put("dateOfBirth", dob);

        // favorite genre
        String genre = safeGet(c, "favoriteGenre", "favorite_genre", "genre", "getFavoriteGenre", "getGenre");
        if (genre != null) out.put("favoriteGenre", genre);

        log.debug("/api/me -> {}", out);
        return ResponseEntity.ok(out);
    }

    private String safeGet(Object obj, String... names) {
        if (obj == null) return null;
        Class<?> cls = obj.getClass();

        for (String n : names) {
            try {
                Method m = cls.getMethod(n);
                Object v = m.invoke(obj);
                if (v != null) {
                    String s = String.valueOf(v).trim();
                    if (!s.isBlank()) return s;
                }
            } catch (NoSuchMethodException ignored) {}
            catch (Exception ex) { log.debug("method invoke {} failed: {}", n, ex.toString()); }
            String getter = "get" + Character.toUpperCase(n.charAt(0)) + (n.length()>1 ? n.substring(1) : "");
            try {
                Method m2 = cls.getMethod(getter);
                Object v = m2.invoke(obj);
                if (v != null) {
                    String s = String.valueOf(v).trim();
                    if (!s.isBlank()) return s;
                }
            } catch (NoSuchMethodException ignored2) {}
            catch (Exception ex) { log.debug("method invoke {} failed: {}", getter, ex.toString()); }
        }

        for (String n : names) {
            try {
                Field f = cls.getDeclaredField(n);
                f.setAccessible(true);
                Object v = f.get(obj);
                if (v != null) {
                    String s = String.valueOf(v).trim();
                    if (!s.isBlank()) return s;
                }
            } catch (NoSuchFieldException ignored) {}
            catch (Exception ex) { log.debug("field access {} failed: {}", n, ex.toString()); }
        }

        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            session.invalidate();
            log.info("session invalidated");
        } catch (IllegalStateException ignored) { }
        return ResponseEntity.ok(Map.of("ok", true));
    }
}