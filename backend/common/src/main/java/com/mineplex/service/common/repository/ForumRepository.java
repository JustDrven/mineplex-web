package com.mineplex.service.common.repository;

import com.mineplex.service.common.entity.forum.Forum;
import com.mineplex.service.common.entity.forum.ForumMessage;
import com.mineplex.service.common.util.CacheObject;

import com.speedment.jpastreamer.application.JPAStreamer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ForumRepository {

    private final CacheObject<List<Forum>> forumCache;

    @Inject
    private JPAStreamer jpaStreamer;
    @Inject
    private EntityManager entityManager;

    public ForumRepository() {
        forumCache = new CacheObject<>(Duration.ofSeconds(3L));
    }

    public Optional<Forum> findForumById(UUID id) {
        Optional<List<Forum>> response = forumCache.getValue();

        return response.map(forums -> forums.stream()
                        .filter((forum) -> forum.getId().equals(id))
                        .findFirst())

                .orElseGet(() -> jpaStreamer.stream(Forum.class)
                        .filter((forum) -> forum.getId().equals(id))
                        .findFirst());

    }

    public List<Forum> getForum() {
        Optional<List<Forum>> response = forumCache.getValue();
        if (response.isPresent()) return response.get();

        List<Forum> fetchFromDb = jpaStreamer.stream(Forum.class).toList();
        forumCache.set(fetchFromDb);

        return fetchFromDb;
    }

    @Transactional
    public void saveForum(Forum forum) {
        entityManager.persist(forum);
    }

    @Transactional
    public void saveForumMessage(ForumMessage forumMessage) {
        entityManager.persist(forumMessage);
    }

}
