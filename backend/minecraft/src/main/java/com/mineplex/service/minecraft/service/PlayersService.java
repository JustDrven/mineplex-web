package com.mineplex.service.minecraft.service;

import com.mineplex.service.common.data.MineplexPlayersData;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlayersService {

    public MineplexPlayersData fetchPlayers() {
        return new MineplexPlayersData(2, 200);
    }
}
