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
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "forums_messages")
public class ForumMessage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToMany
    @JoinColumn(name = "sender_id")
    @Column(nullable = false)
    private Account sender;

    @Column(nullable = false)
    private String message;

    private Date sentAt;

    public ForumMessage() {
    }

    public ForumMessage(Forum forum, Account sender, String message) {
        this.forum = forum;
        this.sender = sender;
        this.message = message;

        sentAt = new Date();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public Forum getForum() {
        return forum;
    }

    public Account getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getSentAt() {
        return sentAt;
    }
}
