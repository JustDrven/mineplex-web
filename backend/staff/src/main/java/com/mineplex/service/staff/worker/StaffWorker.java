package com.mineplex.service.staff.worker;

import com.mineplex.service.common.entity.main.Account;
import com.mineplex.service.common.entity.main.Rank;
import com.mineplex.service.common.rank.RankCategory;
import com.mineplex.service.common.rank.RankGroup;
import com.mineplex.service.common.repository.AccountRepository;
import com.mineplex.service.common.util.CacheObject;
import com.mineplex.service.staff.dto.StaffDTO;

import com.mineplex.service.staff.dto.StaffRank;
import com.mineplex.service.staff.dto.StaffUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StaffWorker {

    private final CacheObject<List<StaffDTO>> staffCache;

    @Inject
    private AccountRepository accountRepository;

    public StaffWorker() {
        staffCache = new CacheObject<>(Duration.ofMinutes(5L));
    }

    public List<StaffDTO> getStaffs() {
        List<StaffDTO> response = staffCache.getValue().orElse(null);
        if (response != null) return response;

        response = fetchFromDatabase();
        staffCache.set(response);

        return response;
    }

    private List<StaffDTO> fetchFromDatabase() {
        List<StaffDTO> toReturn = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll((account) -> {
            List<Rank> ranks = account.getRanks();
            if (ranks == null) return false;
            if (ranks.isEmpty()) return false;

            return ranks.stream().anyMatch(Rank::isAdmin);
        });



        for (RankCategory currentRankCategory : RankCategory.getValues()) {
            List<StaffUser> staffUsers = getStaffUsers(
                    currentRankCategory, accounts
            );

            if (staffUsers.isEmpty()) continue;

            toReturn.add(new StaffDTO(currentRankCategory.getDisplay(), staffUsers));
        }

        return toReturn;
    }

    private @NonNull List<StaffUser> getStaffUsers(RankCategory currentRankCategory, List<Account> accounts) {
        List<StaffUser> staffUsers = new ArrayList<>();

        for (Account account : accounts) {
            List<Rank> ranks = account.getRanks();
            RankGroup foundedRank = null;

            for (Rank rank : ranks) {

                RankGroup currentRankGroup = rank.getRank();
                if (!currentRankCategory.containsRank(currentRankGroup)) continue;

                foundedRank = currentRankGroup;
                break;
            }

            if (foundedRank == null) continue;

            staffUsers.add(new StaffUser(
                    account.getName(),

                    new StaffRank(
                            foundedRank.getDisplay(), calculatePriority(foundedRank)
                    )
            ));

        }


        return staffUsers;
    }

    private int calculatePriority(RankGroup rank) {
        return rank.ordinal() + 1;
    }
}
