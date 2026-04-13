package com.mineplex.service.common.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public final class CacheObject<T> {

    private T value = null;
    private final Duration duration;
    private Instant lastUpdate = null;

    public CacheObject(Duration duration) {
        this.duration = duration;
    }

    public void set(T value) {
        this.value = value;
        lastUpdate = Instant.now();
    }

    public Optional<T> getValue() {
        if (expire()) return Optional.empty();

        return Optional.ofNullable(value);
    }

    private boolean expire() {
        if (lastUpdate == null) return true;
        Duration between = Duration.between(lastUpdate, Instant.now());

        return between.compareTo(duration) > 0;
    }

}
