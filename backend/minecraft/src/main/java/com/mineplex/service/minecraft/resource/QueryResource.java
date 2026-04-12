package com.mineplex.service.minecraft.resource;

import com.mineplex.service.common.data.MineplexPlayersData;
import com.mineplex.service.minecraft.service.PlayersService;

import jakarta.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class QueryResource {

    @Inject
    private PlayersService playersService;

    @Query("players")
    public MineplexPlayersData getPlayers() {
        return playersService.fetchPlayers();
    }

}
