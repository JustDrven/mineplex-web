package com.mineplex.service.common.rank;

import java.util.EnumSet;
import java.util.List;

public enum RankCategory {

    LEADER_SHIP("Leader-Ship", List.of(RankGroup.OWNER, RankGroup.LEADER, RankGroup.ADMIN)),
    DEVELOPMENT("Development", List.of(RankGroup.DEVELOPER)),

    MODERATORS("Moderators", List.of(RankGroup.MOD)),
    BUILDERS("Builders", List.of(RankGroup.BUILD_LEAD, RankGroup.BUILD_TEAM)),

    ;

    private static final EnumSet<RankCategory> VALUES = EnumSet.allOf(RankCategory.class);
    private final String display;
    private final List<RankGroup> rankGroups;

    RankCategory(String display, List<RankGroup> rankGroups) {
        this.display = display;
        this.rankGroups = rankGroups;
    }

    public static EnumSet<RankCategory> getValues() {
        return VALUES;
    }

    public boolean containsRank(RankGroup currentRanks) {
        return rankGroups.contains(currentRanks);
    }

    public List<RankGroup> getRankGroups() {
        return rankGroups;
    }

    public String getDisplay() {
        return display;
    }
}
