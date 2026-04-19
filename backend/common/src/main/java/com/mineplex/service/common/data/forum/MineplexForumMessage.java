package com.mineplex.service.common.data.forum;

public record MineplexForumMessage(String sentAt,
                                   MineplexForumUser sender,
                                   String content) {
}
