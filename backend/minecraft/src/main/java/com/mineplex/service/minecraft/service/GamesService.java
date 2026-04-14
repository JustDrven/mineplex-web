package com.mineplex.service.minecraft.service;

import com.mineplex.service.common.data.MineplexGame;
import com.mineplex.service.common.entity.main.game.Environment;
import com.mineplex.service.common.entity.main.game.Game;
import com.mineplex.service.common.repository.GameRepository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.jetbrains.annotations.Nullable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GamesService {

    private final Cache<UUID, MineplexGame> mineplexGameCache;
    @Inject
    private GameRepository gameRepository;

    public GamesService() {
        mineplexGameCache = Caffeine.newBuilder()
                .maximumSize(50)
                .expireAfterWrite(Duration.ofHours(5L))
                .build();

    }

    public List<MineplexGame> getGames() {
        return mineplexGameCache.asMap().values().stream().toList();
    }

    public Optional<MineplexGame> getGameByID(UUID id) {
        MineplexGame response = mineplexGameCache.getIfPresent(id);
        if (response == null)
            response = fetchGame(id);

        return Optional.ofNullable(response);
    }

    private @Nullable MineplexGame fetchGame(UUID id) {
        Game foundedGame = gameRepository.findGameById(id);
        if (foundedGame == null) return null;

        MineplexGame response = mapToDTO(foundedGame);
        mineplexGameCache.put(id, response);

        return response;
    }

    private MineplexGame mapToDTO(Game game) {
        String id = game.getId().toString();
        String displayName = game.getDisplayName();

        Environment env = game.getEnvironment();
        String image = "https://images.mineplex.com/images/games/%s/%s.png".formatted(
                id, game.getName().replace(" ", "-")
        );

        return new MineplexGame(
                id,

                image,
                displayName,

                env.name()
        );
    }

}
