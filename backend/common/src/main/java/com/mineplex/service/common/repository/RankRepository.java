package com.mineplex.service.common.repository;

import com.mineplex.service.common.entity.main.Rank;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RankRepository {

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void save(Rank rank) {
        entityManager.persist(rank);
    }


}
