package com.mineplex.service.common.entity.main;

import com.mineplex.service.common.rank.RankGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID uuid;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RankGroup rank;

    public Rank(Account account, RankGroup rank) {
        this.account = account;
        this.rank = rank;
    }

    public Rank() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public Account getAccount() {
        return account;
    }

    public RankGroup getRank() {
        return rank;
    }

    public void setRank(RankGroup rank) {
        if (rank == null) rank = RankGroup.PLAYER;

        this.rank = rank;
    }

    public boolean isAdmin() {
        if (rank == null) return false;

        return rank.isAdmin();
    }
}
