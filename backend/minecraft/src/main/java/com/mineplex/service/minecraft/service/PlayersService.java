package com.mineplex.service.minecraft.service;
//

import com.mineplex.service.common.data.main.MineplexPlayersData;
import com.mineplex.service.common.util.CacheObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PlayersService {

    private static final String BASE_URL = "https://api.mcsrvstat.us/3/play.mineplex.com";
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayersService.class);

    private final CacheObject<MineplexPlayersData> playersDataCache;
    private final OkHttpClient pool;

    public PlayersService() {
        playersDataCache = new CacheObject<>(Duration.ofSeconds(10L));
        pool = new OkHttpClient();
    }

    public MineplexPlayersData fetchPlayers() throws Exception {
        Optional<MineplexPlayersData> valueOptional = playersDataCache.getValue();
        MineplexPlayersData response;

        if (valueOptional.isEmpty()) {
            response = fetchPlayersFromAPI().get(4L, TimeUnit.SECONDS);
            playersDataCache.set(response);
        } else {
            response = valueOptional.get();
        }

        return response;
    }

    private CompletableFuture<MineplexPlayersData> fetchPlayersFromAPI() {
        return CompletableFuture.supplyAsync(() -> {
            Request httpRequest = new Request.Builder()
                    .url(BASE_URL)
                    .get()
                    .header("Content-Type", "appliction/json")
                    .build();

            try (Response response = pool.newCall(httpRequest).execute()) {

                String data = response.body().string();
                LOGGER.info("Getting response {}", "GET " + BASE_URL);

                JsonObject jsonData = JsonParser.parseString(data).getAsJsonObject();
                JsonObject players = jsonData.get("players").getAsJsonObject();

                int online = players.get("online").getAsInt();
                int max = players.get("max").getAsInt();

                return new MineplexPlayersData(online, max);
            } catch (Throwable throwable) {
                LOGGER.error("Failed to get data from api", throwable);
                return null;
            }
        });
    }
}
