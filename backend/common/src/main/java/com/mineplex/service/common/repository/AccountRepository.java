package com.mineplex.service.common.repository;

import com.mineplex.service.common.entity.main.Account;

import com.speedment.jpastreamer.application.JPAStreamer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.function.Predicate;

@ApplicationScoped
public class AccountRepository {

    @Inject
    private JPAStreamer jpaStreamer;


    public List<Account> findAll(Predicate<Account> accountPredicate) {
        return jpaStreamer.stream(Account.class)
                .filter(accountPredicate)
                .toList();
    }

}
