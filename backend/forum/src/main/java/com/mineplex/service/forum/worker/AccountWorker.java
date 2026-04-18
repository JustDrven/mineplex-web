package com.mineplex.service.forum.worker;

import com.github.benmanes.caffeine.cache.Cache;

import com.github.benmanes.caffeine.cache.Caffeine;

import com.mineplex.service.common.data.main.MineplexAccount;
import com.mineplex.service.common.data.main.MineplexDetail;
import com.mineplex.service.common.data.main.MineplexJwtResponse;
import com.mineplex.service.common.data.main.MineplexRank;
import com.mineplex.service.common.data.response.MineplexPagedAccounts;
import com.mineplex.service.common.data.response.OkReplay;
import com.mineplex.service.common.entity.main.Account;
import com.mineplex.service.common.rank.RankGroup;
import com.mineplex.service.common.repository.AccountRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountWorker {

    @Inject
    private AccountRepository accountRepository;

    private Cache<Long, Account> accountCache;

    public AccountWorker() {
        accountCache = Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(Duration.ofSeconds(20L))
                .build();
    }

    public OkReplay register(String name, String password) {
        try {
            String hashPassword = hashPassword(password);
            Account account = new Account(name, hashPassword);

            accountRepository.save(account);

            return new OkReplay(
                    true,
                    "Your account was successfully created!"
            );
        } catch (Throwable throwable) {
            return new OkReplay(
                    true,
                    throwable.getMessage()
            );
        }

    }

    public Account getAccount(long id) {
        Account response = accountCache.getIfPresent(id);
        if (response != null) return response;

        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) return null;

        response = accountOptional.get();
        accountCache.put(id, response);

        return response;

    }

    @Transactional
    public MineplexPagedAccounts getAccounts(int page, int perPage) {
        List<MineplexAccount> mappedAccounts = accountRepository.findAllLimit(page, perPage).stream()
                .map((account) -> {

                    List<MineplexRank> ranks;
                    if (account.getRanks() != null && account.getRanks().isEmpty())
                        ranks = account.getRanks().stream().map((rank) -> {
                            RankGroup rankGroup = rank.getRank();
                            return new MineplexRank(
                                    rankGroup.getDisplay(),
                                    rankGroup.ordinal()
                            );
                        }).toList();
                    else
                        ranks = List.of();


                    return new MineplexAccount((int) account.getId(), account.getName(), ranks, List.of());
                }).toList();


        return new MineplexPagedAccounts(
                page,
                perPage,

                mappedAccounts
        );
    }

    private boolean arePasswordSame(String password, String hashPassword) {
        return false;
    }

    private String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    public MineplexJwtResponse login(String name, String password) {
        Optional<Account> accountOptional = accountRepository.findByName(name);
        if (accountOptional.isEmpty()) return new MineplexJwtResponse(null, new MineplexDetail("error", "The account doesn't exist!"));

        Account account = accountOptional.get();
        if (!arePasswordSame(password, account.getPassword())) return new MineplexJwtResponse(null, new MineplexDetail("error", "Passwords aren't some!"));

        String token = generateToken(account);
        return new MineplexJwtResponse(
                token,
                new MineplexDetail("success", "Logged!")
        );
    }

    private String generateToken(Account account) {
        return "";
    }
}
