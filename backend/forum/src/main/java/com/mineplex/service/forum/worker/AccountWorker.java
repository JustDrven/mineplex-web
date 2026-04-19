package com.mineplex.service.forum.worker;

import com.mineplex.service.common.data.main.MineplexAccount;
import com.mineplex.service.common.data.main.MineplexDetail;
import com.mineplex.service.common.data.main.MineplexJwtResponse;
import com.mineplex.service.common.data.main.MineplexRank;
import com.mineplex.service.common.data.response.MineplexPagedAccounts;
import com.mineplex.service.common.data.response.OkReplay;
import com.mineplex.service.common.entity.main.Account;
import com.mineplex.service.common.entity.main.Rank;
import com.mineplex.service.common.rank.RankGroup;
import com.mineplex.service.common.repository.AccountRepository;
import com.mineplex.service.common.repository.RankRepository;
import com.mineplex.service.common.worker.JwtWorker;
import com.mineplex.service.common.worker.PasswordWorker;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class AccountWorker {

    private final Cache<Long, Account> accountCache;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private RankRepository rankRepository;
    @Inject
    private PasswordWorker passwordWorker;
    @Inject
    private JwtWorker jwtWorker;

    public AccountWorker() {
        accountCache = Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(Duration.ofSeconds(20L))
                .build();
    }

    public OkReplay register(String name, String password) {
        try {
            Account account = new Account(name, hashPassword(password));
            Rank accountRank = new Rank(account, RankGroup.PLAYER);

            accountRepository.save(account);
            rankRepository.save(accountRank);

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
        return accountCache.get(id, (accountId) -> {
            Optional<Account> accountOptional = accountRepository.findById(accountId);
            if (accountOptional.isEmpty()) throw new RuntimeException("The account doesn't exist");

            return accountOptional.get();
        });

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

    private boolean isPasswordSame(String password, String hashPassword) {
        return passwordWorker.verify(hashPassword, password);
    }

    private String hashPassword(String password) {
        return passwordWorker.hash(password);
    }

    public MineplexJwtResponse login(String name, String password) {
        Optional<Account> accountOptional = accountRepository.findByName(name);
        if (accountOptional.isEmpty())
            return new MineplexJwtResponse(null, new MineplexDetail("error", "The account doesn't exist!"));

        Account account = accountOptional.get();
        if (!isPasswordSame(password, account.getPassword()))
            return new MineplexJwtResponse(null, new MineplexDetail("error", "Passwords aren't some!"));

        String token = generateToken(account);
        return new MineplexJwtResponse(
                token,
                new MineplexDetail("success", "Logged!")
        );
    }

    private String generateToken(Account account) {
        return jwtWorker.encode((builder) -> {

            builder.withClaim("accountId", account.getId());
            builder.withExpiresAt(Instant.now().plusSeconds(
                    TimeUnit.HOURS.toSeconds(20L))
            );

        });
    }
}
