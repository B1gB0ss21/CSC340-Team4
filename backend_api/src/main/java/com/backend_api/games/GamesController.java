package com.backend_api.games;
import com.backend_api.games.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final GamesRepository gamesRepository;
    
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
    public ResponseEntity<List<Games>> searchGames(
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "price", required = false) Double price
    ) {
        List<Games> result;
        if (q != null && !q.isEmpty() && price != null) {
            result = gamesRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(q, price);
        } else if (q != null && !q.isEmpty()) {
            result = gamesRepository.findByNameContainingIgnoreCase(q);
        } else if (price != null) {
            result = gamesRepository.findByPriceLessThanEqual(price);
        } else {
            result = gamesService.getAvailableGames();
        }
        return ResponseEntity.ok(result);
    }
}