package com.mineplex.service.common.rank;

import java.util.EnumSet;
import java.util.Optional;

public enum RankGroup {

    OWNER("Owner"),
    LEADER("Leader"),
    ADMIN("Admin"),
    DEVELOPER("Developer"),
    SUPPORT("Support"),
    CLANS_MANAGER("ClansManager"),
    COMMUNITY_MANAGER("CommunityManager"),
    FORUM_MANAGER("ForumManager"),
    STAFF_MANAGER("StaffManager"),
    RECRUITER("Recruiter"),
    EVENT_SQUAD("EventSquad"),
    QA("QA"),
    SOCIAL_MEDIA("SocialMedia"),
    MOD("Mod"),
    TRAINEE("Trainee"),
    BUILD_LEAD("BuildLead"),
    BUILD_TEAM("BuildTeam"),

    PLAYER("Player", false);

    private static final EnumSet<RankGroup> VALUES = EnumSet.allOf(RankGroup.class);
    private final String display;
    private final boolean admin;

    RankGroup(String display, boolean admin) {
        this.display = display;
        this.admin = admin;
    }

    RankGroup(String display) {
        this(display, true);
    }

    public static Optional<RankGroup> find(String name) {
        return VALUES.stream()
                .filter((rankGroup) -> rankGroup.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getDisplay() {
        return display;
    }
}
