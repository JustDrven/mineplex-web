package com.mineplex.service.common.data.forum;

import java.util.List;

public record MineplexForum(String id,
                            String title,
                            boolean open,

                            List<MineplexForumMessage> messages) {
}
