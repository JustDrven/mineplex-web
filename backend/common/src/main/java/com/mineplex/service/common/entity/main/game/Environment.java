package com.mineplex.service.common.entity.main.game;

import java.util.EnumSet;
import java.util.Optional;

public enum Environment {

    RELEASED,
    CLOSED,
    IN_DEVELOPMENT;

    private static final EnumSet<Environment> VALUES = EnumSet.allOf(Environment.class);

    public static Optional<Environment> find(String name) {
        return VALUES.stream()
                .filter((environment) -> environment.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean isPublic() {
        return equals(RELEASED);
    }
}
