package com.mineplex.service.common.repository;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;

import com.github.benmanes.caffeine.cache.Caffeine;

import com.mineplex.service.common.entity.forum.Forum;
import com.mineplex.service.common.entity.forum.ForumMessage;

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

    @Inject
    private JPAStreamer jpaStreamer;
    @Inject
    private EntityManager entityManager;

    private final Cache<UUID, Forum> forumCache;

    public ForumRepository() {
        forumCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(10L))
                .maximumSize(1_000L)
                .build();
    }

    public Optional<Forum> findForumById(UUID id) {
        Forum cacheResponse = forumCache.getIfPresent(id);
        if (cacheResponse != null) return Optional.of(cacheResponse);

        Optional<Forum> forumOptional = jpaStreamer.stream(Forum.class)
                .filter((forum) -> forum.getId().equals(id))
                .findFirst();

        forumOptional.ifPresent((forum) -> forumCache.put(forum.getId(), forum));

        return forumOptional;

    }

    @Transactional
    public void deleteForum(UUID uuid) {
        Optional<Forum> forumOptional = findForumById(uuid);

        forumOptional.ifPresent(forum -> {
            Forum forumToRemove = (entityManager.contains(forum) ? forum : entityManager.merge(forum));

            entityManager.remove(forumToRemove);
        });
    }

    public List<Forum> getForum() {
        return jpaStreamer.stream(Forum.class)
                .toList();
    }


    @Transactional
    public void saveForum(Forum forum) {
        entityManager.persist(forum);

        forumCache.put(forum.getId(), forum);
    }

    @Transactional
    public void saveForumMessage(ForumMessage forumMessage) {
        Forum forum = forumMessage.getForum();
        if (forum != null) {
            UUID id = forum.getId();

            forumCache.invalidate(id);
        }

        entityManager.persist(forumMessage);
    }

}
