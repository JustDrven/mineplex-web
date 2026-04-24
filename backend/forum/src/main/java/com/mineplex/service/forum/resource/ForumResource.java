package com.mineplex.service.forum.resource;

import com.mineplex.service.common.data.forum.MineplexForum;
import com.mineplex.service.common.data.response.OkReplay;
import com.mineplex.service.forum.worker.ForumWorker;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@GraphQLApi
public class ForumResource {

    @Inject
    private ForumWorker forumWorker;

    @Mutation("createForum")
    public MineplexForum createForum(@Name("title") String title, @Name("token") String token) {
        return forumWorker.createForum(title, token);
    }

    @Mutation("deleteForum")
    public OkReplay deleteForum(@Name("id") String id, @Name("token") String token) {
        return forumWorker.deleteForum(id, token);
    }

    @Query("sendForumMessage")
    public OkReplay sendForumMessage(@Name("token") String token, @Name("forumId") UUID id, @Name("content") String content) {
        return forumWorker.sendForumMessage(token, id, content);
    }

    @Query("forums")
    public List<MineplexForum> getForums(@Name("page") int page, @Name("perPage") int perPage) {
        return forumWorker.getForum(page, perPage);
    }

    @Query("forumsByCreator")
    public List<MineplexForum> getForumsByCreator(@Name("creatorId") int creatorId) {
        return forumWorker.getForumByCreator(creatorId);
    }

}
