package com.backend_api.developer;

import com.backend_api.games.Games;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "developers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    @Column(unique = true, nullable = false)
    private String email;

    // Hide password from JSON output (write-only)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password required")
    private String password;

    private String dob;  // date of birth (string like 1990-01-01)

    // Developer has MANY games
    // Your Games entity uses "developer" on the ManyToOne side
    @JsonIgnore // prevents infinite recursion when serializing
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private List<Games> games = new ArrayList<>();

    // Convenience constructor (optional)
    public Developer(Long id) {
        this.id = id;
    }
}
