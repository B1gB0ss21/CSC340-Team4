package com.csc340.localharvest_hub.producebox;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(gamesService.createGames(games));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Games> updateGames(@PathVariable Long id,
                                             @Valid @RequestBody Games gameDetails) {
        return ResponseEntity.ok(gamesService.updateGames(id, gameDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGames(@PathVariable Long id) {
        gamesService.deleteGames(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Games> getGames(@PathVariable Long id) {
        return ResponseEntity.ok(gamesService.getGamesById(id));
    }

    @GetMapping
    public ResponseEntity<List<Games>> getAvailableGames() {
        return ResponseEntity.ok(gamesService.getAvailableGames());
    }
}
