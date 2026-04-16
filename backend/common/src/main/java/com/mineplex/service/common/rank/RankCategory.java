package com.mineplex.service.common.rank;

import java.util.EnumSet;
import java.util.List;

public enum RankCategory {

    LEADER_SHIP("Leader-Ship", List.of(RankGroup.OWNER, RankGroup.LEADER)),
    DEVELOPMENT("Development", List.of(RankGroup.DEVELOPER)),

    MODERATORS("Moderators", List.of(RankGroup.MODERATOR)),
    BUILDERS("Builders", List.of(RankGroup.BUILDER)),

    ;

    private static final EnumSet<RankCategory> VALUES = EnumSet.allOf(RankCategory.class);

    public static EnumSet<RankCategory> getValues() {
        return VALUES;
    }

    private final String display;
    private final List<RankGroup> rankGroups;

    RankCategory(String display, List<RankGroup> rankGroups) {
        this.display = display;
        this.rankGroups = rankGroups;
    }

    public boolean containsRank(List<RankGroup> currentRanks) {
        return currentRanks.stream().anyMatch((rankGroup) -> getRankGroups().contains(rankGroup));
    }

    public List<RankGroup> getRankGroups() {
        return rankGroups;
    }

    public String getDisplay() {
        return display;
    }
}
