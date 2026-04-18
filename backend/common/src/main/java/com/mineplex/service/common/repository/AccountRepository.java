package com.mineplex.service.common.repository;

import com.mineplex.service.common.entity.main.Account;

import com.mineplex.service.common.entity.main.Rank;

import com.speedment.jpastreamer.application.JPAStreamer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@ApplicationScoped
public class AccountRepository {

    @Inject
    private JPAStreamer jpaStreamer;

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void save(Account account) {
        entityManager.persist(account);

        for (Rank rank : account.getRanks()) {
            entityManager.persist(rank);
        }
    }

    @Transactional
    public List<Account> findAllLimit(int size, int max) {
        int offset = (size - 1) * max;

        return jpaStreamer.stream(Account.class)
                .skip(offset)
                .limit(max)
                .toList();
    }

    @Transactional
    public List<Account> findAll(Predicate<Account> accountPredicate) {
        return jpaStreamer.stream(Account.class)
                .filter(accountPredicate)
                .toList();
    }

    public Optional<Account> findByName(String name) {
        return jpaStreamer.stream(Account.class)
                .filter((account) -> account.getName().equals(name))
                .findFirst();
    }

    public Optional<Account> findById(long id) {
        return jpaStreamer.stream(Account.class)
                .filter((account) -> account.getId() == id)
                .findFirst();
    }
}
