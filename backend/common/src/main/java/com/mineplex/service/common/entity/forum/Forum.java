package com.mineplex.service.common.entity.forum;

import com.mineplex.service.common.entity.main.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "forums")
public class Forum {

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private Account creator;

    @OneToMany
    private List<ForumMessage> messages;

    private boolean open;

    private Date createdAt;

    public Forum(String title, Account creator) {
        this.title = title;
        this.creator = creator;

        messages = new ArrayList<>();
        open = true;
        createdAt = new Date();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Account getCreator() {
        return creator;
    }

    public List<ForumMessage> getMessages() {
        return messages;
    }

    public boolean isOpen() {
        return open;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
