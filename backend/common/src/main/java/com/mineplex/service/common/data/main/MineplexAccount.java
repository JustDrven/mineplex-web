package com.mineplex.service.common.data.main;

import com.mineplex.service.common.data.forum.MineplexForum;

import java.util.List;

public record MineplexAccount(int id,
                              String name,

                              List<MineplexRank> ranks,
                              List<MineplexForum> forums) {
}
