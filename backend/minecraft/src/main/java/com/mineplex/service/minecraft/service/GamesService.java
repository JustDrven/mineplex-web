package com.mineplex.service.minecraft.service;

import com.mineplex.service.common.data.MineplexGame;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GamesService {

    private final Cache<UUID, MineplexGame> mineplexGameCache;

    public GamesService() {
        mineplexGameCache = Caffeine.newBuilder()
                .maximumSize(50)
                .expireAfterWrite(Duration.ofHours(5L))
                .build();

    }

    public Collection<MineplexGame> getGames() {
        return mineplexGameCache.asMap().values();
    }

    public Optional<MineplexGame> getGameByID(UUID id) {
        return Optional.ofNullable(mineplexGameCache.getIfPresent(id));
    }

}
