package com.mineplex.service.forum.worker;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.mineplex.service.common.data.forum.MineplexForum;
import com.mineplex.service.common.data.forum.MineplexForumMessage;
import com.mineplex.service.common.data.forum.MineplexForumUser;
import com.mineplex.service.common.data.response.OkReplay;
import com.mineplex.service.common.entity.forum.Forum;
import com.mineplex.service.common.entity.forum.ForumMessage;
import com.mineplex.service.common.entity.main.Account;
import com.mineplex.service.common.rank.RankGroup;
import com.mineplex.service.common.repository.ForumRepository;
import com.mineplex.service.common.worker.JwtWorker;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@ApplicationScoped
public class ForumWorker {

    private static final RankGroup[] DELETE_FORUM_ALLOW_RANKS = {

            RankGroup.OWNER,
            RankGroup.LEADER,
            RankGroup.ADMIN,
            RankGroup.DEVELOPER,

            RankGroup.SUPPORT,
            RankGroup.MOD,

            RankGroup.STAFF_MANAGER,
            RankGroup.COMMUNITY_MANAGER,

    };

    @Inject
    private ForumRepository forumRepository;

    @Inject
    private AccountWorker accountWorker;
    @Inject
    private JwtWorker jwtWorker;

    private final ExecutorService forumThreadPool;

    public ForumWorker() {
        forumThreadPool = Executors.newFixedThreadPool(10);
    }

    public List<MineplexForum> getForum() {
        List<Forum> forum = forumRepository.getForum();

        return forum.stream()
                .map(mapDTO())
                .toList();
    }

    @Transactional
    public List<MineplexForum> getForum(int page, int perPage) {
        List<Forum> forum = forumRepository.getForum();
        int calculateSkip = Math.max(0, page - 1) * perPage;

        return forum.stream()

                .skip(calculateSkip)
                .limit(perPage)

                .map(mapDTO())
                .toList();
    }

    public List<MineplexForum> getForumByCreator(int creatorId) {
        return forumRepository.getForum().stream()
                .filter((forum) -> forum.getCreator().getId() == creatorId)
                .map(mapDTO())
                .toList();
    }

    private MineplexForum deserialize(Forum forum) {
        String id = forum.getId().toString();
        String title = forum.getTitle();

        boolean open = forum.isOpen();

        List<MineplexForumMessage> messages;
        if (forum.getMessages() != null)
            messages = forum.getMessages().stream().map((fm) -> {

                MineplexForumUser user = new MineplexForumUser(
                        ((int) fm.getSender().getId()), fm.getSender().getName()
                );
                String content = fm.getMessage();
                String sentAt = fm.getSentAt().toString();

                return new MineplexForumMessage(
                        sentAt,

                        user,
                        content

                );
            }).toList();
        else
            messages = List.of();

        return new MineplexForum(
                id,

                title,
                open,

                messages
        );
    }

    private Function<Forum, MineplexForum> mapDTO() {
        return this::deserialize;
    }

    public MineplexForum createForum(String title, String token) {
        if (!jwtWorker.verify(token)) return null;

        int accountId = extractAccountId(token);
        Optional<Account> accountOptional = accountWorker.getAccount(accountId);
        if (accountOptional.isEmpty()) return null;

        Account account = accountOptional.get();
        Forum forum = new Forum(title, account);

        forumRepository.saveForum(forum);

        return deserialize(forum);
    }

    public OkReplay deleteForum(String id, String token) {
        if (!jwtWorker.verify(token)) return new OkReplay(true, "The JWT already expire!");

        Optional<Forum> forumOptional = forumRepository.findForumById(UUID.fromString(id));
        if (forumOptional.isEmpty()) return new OkReplay(true, "The forum doesn't exist");

        int accountId = extractAccountId(token);
        if (accountId == -1) return new OkReplay(true, "The account doesn't exist");

        Forum forum = forumOptional.get();
        if (!ownForum(forum, accountId)) return new OkReplay(true, "The account isn't creator");

        forumThreadPool.execute(() -> forumRepository.deleteForum(forum.getId()));

        return new OkReplay(false, "The forum %s has been deleted!".formatted(forum.getId().toString()));
    }

    private boolean ownForum(Forum forum, int accountId) {
        long creatorId = forum.getCreator().getId();
        if (creatorId == accountId) return true;

        Optional<Account> accountOptional = accountWorker.getAccount(accountId);

        return accountOptional
                .filter(account -> RankGroup.has(account, DELETE_FORUM_ALLOW_RANKS))
                .isPresent();
    }

    private int extractAccountId(String token) {
        DecodedJWT jwtData = jwtWorker.decode(token);
        Claim claim = jwtData.getClaim("accountId");

        return (claim != null) ? claim.asInt() : -1;
    }


    @Transactional
    public OkReplay sendForumMessage(String token, UUID id, String content) {
        if (!jwtWorker.verify(token)) return new OkReplay(true, "The JWT already expire!");

        Optional<Forum> forumOptional = forumRepository.findForumById(id);
        if (forumOptional.isEmpty()) return new OkReplay(true, "The forum doesn't exist");

        Optional<Account> accountOptional = accountWorker.getAccount(extractAccountId(token));
        if (accountOptional.isEmpty()) return new OkReplay(true, "The account doesn't exist!");


        Forum forum = forumOptional.get();
        Account account = accountOptional.get();

        ForumMessage forumMessage = new ForumMessage(
                forum,
                account,
                content
        );

        forumThreadPool.execute(() -> forumRepository.saveForumMessage(forumMessage));

        return new OkReplay(false, "The message has been successfully sent!");
    }
}
