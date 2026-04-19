package com.mineplex.service.forum.resource;

import com.mineplex.service.common.data.forum.MineplexForum;
import com.mineplex.service.forum.worker.ForumWorker;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import jakarta.inject.Inject;

import java.util.List;

@GraphQLApi
public class ForumResource {

    @Inject
    private ForumWorker forumWorker;

    @Query("forums")
    public List<MineplexForum> getForums(@Name("page") int page, @Name("perPage") int perPage) {
        return forumWorker.getForum(page, perPage);
    }

    @Query("forumsByCreator")
    public List<MineplexForum> getForumsByCreator(@Name("creatorId") int creatorId) {
        return forumWorker.getForumByCreator(creatorId);
    }

}
