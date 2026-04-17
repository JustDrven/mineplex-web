package com.mineplex.service.common.repository;

import com.mineplex.service.common.entity.main.game.Game;

import com.speedment.jpastreamer.application.JPAStreamer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GameRepository {

    @Inject
    private JPAStreamer streamer;

    public Game findGameById(UUID id) {
        return streamer.stream(Game.class)
                .filter((game) -> game.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Game> findGames() {
        return streamer.stream(Game.class)
                .toList();
    }
}
