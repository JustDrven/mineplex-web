package com.mineplex.service.forum.resource;

import com.mineplex.service.forum.worker.AccountWorker;

import org.eclipse.microprofile.graphql.GraphQLApi;

import jakarta.inject.Inject;

@GraphQLApi
public class AccountResource {

    @Inject
    private AccountWorker accountWorker;


}
