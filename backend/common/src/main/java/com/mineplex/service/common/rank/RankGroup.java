package com.mineplex.service.common.rank;

import java.util.EnumSet;
import java.util.Optional;

public enum RankGroup {

    OWNER("Owner"),
    LEADER("Leader"),
    DEVELOPER("Developer"),

    MODERATOR("Moderator"),
    BUILDER("Builder"),

    VIP("VIP", false),
    PLAYER("Player", false)

    ;

    private final String display;
    private final boolean admin;

    RankGroup(String display, boolean admin) {
        this.display = display;
        this.admin = admin;
    }

    RankGroup(String display) {
        this(display, true);
    }

    public boolean isAdmin() {
        return admin;
    }

    private static final EnumSet<RankGroup> VALUES = EnumSet.allOf(RankGroup.class);

    public static Optional<RankGroup> find(String name) {
        return VALUES.stream()
                .filter((rankGroup) -> rankGroup.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public String getDisplay() {
        return display;
    }
}
