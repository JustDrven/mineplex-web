package com.mineplex.service.forum.resource;

import com.mineplex.service.forum.worker.ForumWorker;

import org.eclipse.microprofile.graphql.GraphQLApi;

import jakarta.inject.Inject;

@GraphQLApi
public class ForumResource {

    @Inject
    private ForumWorker forumWorker;


}
