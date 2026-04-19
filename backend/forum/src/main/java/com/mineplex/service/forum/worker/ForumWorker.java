package com.mineplex.service.forum.worker;

import com.mineplex.service.common.data.forum.MineplexForum;
import com.mineplex.service.common.data.forum.MineplexForumMessage;
import com.mineplex.service.common.data.forum.MineplexForumUser;
import com.mineplex.service.common.entity.forum.Forum;
import com.mineplex.service.common.repository.ForumRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class ForumWorker {

    @Inject
    private ForumRepository forumRepository;

    public List<MineplexForum> getForum() {
        List<Forum> forum = forumRepository.getForum();

        return forum.stream()
                .map(mapDTO())
                .toList();
    }

    public List<MineplexForum> getForum(int page, int perPage) {
        List<Forum> forum = forumRepository.getForum();
        int calculateSkip = (Math.max(0, page - 1)) * perPage;

        return forum.stream()

                .skip(calculateSkip)
                .limit(perPage)

                .map(mapDTO())
                .toList();
    }

    public List<MineplexForum> getForumByCreator(int creatorId) {
        return forumRepository.getForum().stream()
                .filter((forum) -> forum.getCreator().getId() == creatorId)
                .map(mapDTO())
                .toList();
    }


    private Function<Forum, MineplexForum> mapDTO() {
        return (forum) -> {
            String id = forum.getId().toString();
            String title = forum.getTitle();

            boolean open = forum.isOpen();

            List<MineplexForumMessage> messages;
            if (forum.getMessages() != null && !forum.getMessages().isEmpty())
                messages = forum.getMessages().stream().map((fm) -> {

                    MineplexForumUser user = new MineplexForumUser(
                            ((int) fm.getSender().getId()), fm.getSender().getName()
                    );
                    String content = fm.getMessage();
                    String sentAt = fm.getSentAt().toString();

                    return new MineplexForumMessage(
                            sentAt,

                            user,
                            content

                    );
                }).toList();
            else
                messages = List.of();

            return new MineplexForum(
                    id,

                    title,
                    open,

                    messages
            );
        };

    }

}
