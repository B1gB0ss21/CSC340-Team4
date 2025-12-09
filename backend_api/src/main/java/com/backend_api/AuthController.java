package com.backend_api;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.backend_api.customer.Customer;
import com.backend_api.customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.backend_api.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Not logged in");
        Customer customer = customerRepository.findByEmail(principal.getName()).orElse(null);
        if (customer == null) return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.ok(customer);
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