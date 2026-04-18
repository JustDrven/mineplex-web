package com.mineplex.service.forum.resource;

import com.mineplex.service.common.data.main.MineplexJwtResponse;
import com.mineplex.service.common.data.response.MineplexPagedAccounts;
import com.mineplex.service.common.data.response.OkReplay;
import com.mineplex.service.forum.worker.AccountWorker;

import org.eclipse.microprofile.graphql.GraphQLApi;

import jakarta.inject.Inject;

import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class AccountResource {

    @Inject
    private AccountWorker accountWorker;

    @Mutation("register")
    public OkReplay register(@Name("name") String name, @Name("password") String password) {
        return accountWorker.register(name, password);
    }

    @Mutation("login")
    public MineplexJwtResponse login(@Name("name") String name, @Name("password") String password) {
        return accountWorker.login(name, password);
    }

    @Query("accounts")
    public MineplexPagedAccounts getAccountsMapped(@Name("page") int page, @Name("perPage") int perPage) {
        return accountWorker.getAccounts(page, perPage);
    }


}
