package com.mineplex.service.minecraft.service;

import com.mineplex.service.common.data.main.MineplexGame;
import com.mineplex.service.common.entity.main.game.Environment;
import com.mineplex.service.common.entity.main.game.Game;
import com.mineplex.service.common.repository.GameRepository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.jetbrains.annotations.Nullable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GamesService {

    private final Cache<UUID, MineplexGame> mineplexGameCache;
    @Inject
    private GameRepository gameRepository;

    @Inject
    private EntityManager entityManager;

    public GamesService() {
        mineplexGameCache = Caffeine.newBuilder()
                .maximumSize(50)
                .expireAfterWrite(Duration.ofDays(1L))
                .build();

    }

    @Transactional
    public void save(Game game) {
        entityManager.persist(game);
    }

    public List<MineplexGame> getGames() {
        Collection<MineplexGame> values = mineplexGameCache.asMap().values();
        if (!values.isEmpty())
            return values.stream().toList();

        List<Game> games = gameRepository.findGames();
        List<MineplexGame> mappedGames = games.stream()
                .map(this::mapToDTO)
                .toList();

        mappedGames.forEach((mineplexGame) -> mineplexGameCache.put(UUID.fromString(mineplexGame.id()), mineplexGame));

        return mappedGames;

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

    public MineplexGame mapToDTO(Game game) {
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
