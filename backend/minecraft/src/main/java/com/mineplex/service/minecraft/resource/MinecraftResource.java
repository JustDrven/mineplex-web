package com.mineplex.service.minecraft.resource;

import com.mineplex.service.common.data.main.MineplexGame;
import com.mineplex.service.common.data.main.MineplexPlayersData;
import com.mineplex.service.minecraft.service.GamesService;
import com.mineplex.service.minecraft.service.PlayersService;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GraphQLApi
public class MinecraftResource {

    private static final MineplexPlayersData EMPTY_DATA = new MineplexPlayersData(-1, -1);
    private static final Logger log = LoggerFactory.getLogger(MinecraftResource.class);

    @Inject
    private PlayersService playersService;
    @Inject
    private GamesService gamesService;

    @Query("players")
    public MineplexPlayersData getPlayers() {

        try {
            return playersService.fetchPlayers();
        } catch (Throwable throwable) {
            log.error("Failed to fetch data", throwable);
            return EMPTY_DATA;
        }

    }

    @Query("games")
    public List<MineplexGame> getGames() {
        return gamesService.getGames();
    }

    @Query("gameById")
    public MineplexGame getGamesByID(@Name("id") UUID id) {
        Optional<MineplexGame> gameOptional = gamesService.getGameByID(id);

        return gameOptional.orElseGet(null);
    }

}
