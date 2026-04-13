package com.mineplex.service.minecraft.resource;

import com.mineplex.service.common.data.MineplexPlayersData;
import com.mineplex.service.minecraft.service.PlayersService;

import jakarta.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GraphQLApi
public class QueryResource {

    private static final MineplexPlayersData EMPTY_DATA = new MineplexPlayersData(-1, -1);
    private static final Logger log = LoggerFactory.getLogger(QueryResource.class);

    @Inject
    private PlayersService playersService;

    @Query("players")
    public MineplexPlayersData getPlayers() {

        try {
            return playersService.fetchPlayers();
        } catch (Throwable throwable) {
            log.error("Failed to fetch data", throwable);
            return EMPTY_DATA;
        }

    }

}
