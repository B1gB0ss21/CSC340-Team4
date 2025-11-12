package com.backend_api.games;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GamesController {
    private final GamesService gamesService;

    @PostMapping
    public ResponseEntity<Games> createGames(@Valid @RequestBody Games games) {
        Games created = gamesService.createGames(games);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Games> updateGames(@PathVariable Long id,
                                             @Valid @RequestBody Games gameDetails) {
        Games updated = gamesService.updateGames(id, gameDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGames(@PathVariable Long id) {
        boolean deleted = gamesService.deleteGames(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Games> getGames(@PathVariable Long id) {
        Games g = gamesService.getGamesById(id);
        if (g == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(g);
    }

    @GetMapping
    public ResponseEntity<List<Games>> getAvailableGames() {
        return ResponseEntity.ok(gamesService.getAvailableGames());
    }
}