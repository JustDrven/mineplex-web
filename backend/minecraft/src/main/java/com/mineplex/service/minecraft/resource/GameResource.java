package com.mineplex.service.minecraft.resource;

import com.mineplex.service.common.data.main.MineplexGame;
import com.mineplex.service.common.entity.main.game.Environment;
import com.mineplex.service.common.entity.main.game.Game;
import com.mineplex.service.minecraft.service.GamesService;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;

import jakarta.inject.Inject;

@GraphQLApi
public class GameResource {

    @Inject
    private GamesService gamesService;

    @Mutation("createGame")
    public MineplexGame createGame(@Name("display") String displayName, @Name("environment") String envValue) {
        Environment environment = Environment.find(envValue)
                .orElse(Environment.CLOSED);

        Game game = new Game(
                displayName.toLowerCase().replace(" ", "_"),
                displayName,

                environment
        );


        gamesService.save(game);
        return gamesService.mapToDTO(game);

    }

}
