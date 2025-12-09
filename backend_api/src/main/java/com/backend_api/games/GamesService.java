package com.backend_api.games;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GamesService {
    private final GamesRepository gamesRepository;

    public Games createGames(Games games) {
        return gamesRepository.save(games);
    }

    // return null if not found (controller expects null -> 404)
    public Games updateGames(Long id, Games gameDetails) {
        Optional<Games> opt = gamesRepository.findById(id);
        if (opt.isEmpty()) return null;
        Games game = opt.get();
        game.setName(gameDetails.getName());
        game.setDescription(gameDetails.getDescription());
        game.setPrice(gameDetails.getPrice());
        game.setAvailable(gameDetails.isAvailable());

        return gamesRepository.save(game);
    }

   public Games updateImage(Long id, String imageUrl) {
       Optional<Games> opt = gamesRepository.findById(id);
       if (opt.isEmpty()) return null;
       Games game = opt.get();
       game.setImage(imageUrl);
        return gamesRepository.save(game);
   }

    public boolean deleteGames(Long id) {
        if (!gamesRepository.existsById(id)) {
            return false;
        }
        gamesRepository.deleteById(id);
        return true;
    }

    public Games getGamesById(Long id) {
        return gamesRepository.findById(id).orElse(null);
    }

    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    public List<Games> getAvailableGames() {
        return gamesRepository.findByAvailable(true);
    }
}
