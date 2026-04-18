package com.mineplex.service.common.data.forum;

import com.mineplex.service.common.data.main.MineplexAccount;

public record MineplexForumMessage(String sentAt,
                                   MineplexAccount sender,
                                   String content) {
}
