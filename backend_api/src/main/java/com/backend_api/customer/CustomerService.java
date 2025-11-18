package com.backend_api.customer;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getEmail() == null) throw new IllegalArgumentException("email required");
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalStateException("email already registered");
        }
        if (customer.getPassword() != null) {
            customer.setPassword(encoder.encode(customer.getPassword()));
        }
        Customer saved = customerRepository.save(customer);
        return saved;
    }

    public Customer authenticate(String email, String rawPassword) {
        if (email == null || rawPassword == null) return null;
        Optional<Customer> opt = customerRepository.findByEmail(email);
        if (opt.isEmpty()) return null;
        Customer c = opt.get();
        if (c.getPassword() == null) return null;
        boolean ok = encoder.matches(rawPassword, c.getPassword());
        return ok ? c : null;
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isEmpty()) return null;
        Customer c = opt.get();
        if (customerDetails.getName() != null) c.setName(customerDetails.getName());
        if (customerDetails.getEmail() != null) c.setEmail(customerDetails.getEmail());
        if (customerDetails.getDateOfBirth() != null) c.setDateOfBirth(customerDetails.getDateOfBirth());
        if (customerDetails.getFavoriteGenre() != null) c.setFavoriteGenre(customerDetails.getFavoriteGenre());

        if (customerDetails.getPassword() != null && !customerDetails.getPassword().isBlank()) {
            c.setPassword(encoder.encode(customerDetails.getPassword()));
        }

        return customerRepository.save(c);
    }
    
    public Optional<Customer> findByEmail(String email) {
         return customerRepository.findByEmail(email);
    }
    
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> searchByDob(String dob) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getDateOfBirth() != null && c.getDateOfBirth().contains(dob))
                .collect(Collectors.toList());
    }

    public boolean deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) return false;
        customerRepository.deleteById(id);
        return true;
    }
}