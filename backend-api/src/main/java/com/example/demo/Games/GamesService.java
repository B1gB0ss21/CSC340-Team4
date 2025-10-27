package com.csc340.localharvest_hub.producebox;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GamesService {
    private final GamesRepository gamesRepository;

    public Games createGames(Games games) {
        return gamesRepository.save(games);
    }

    public Games updateGames(Long id, Games gameDetails) {
        Games game = gamesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        game.setName(gameDetails.getName());
        game.setDescription(gameDetails.getDescription());
        game.setPrice(gameDetails.getPrice());
        game.setAvailable(gameDetails.isAvailable());

        return gamesRepository.save(game);
    }

    public void deleteGames(Long id) {
        if (!gamesRepository.existsById(id)) {
            throw new EntityNotFoundException("Game not found");
        }
        gamesRepository.deleteById(id);
    }

    public Games getGamesById(Long id) {
        return gamesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
    }

    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    public List<Games> getAvailableGames() {
        return gamesRepository.findByAvailable(true);
    }
}
