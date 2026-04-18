package com.mineplex.service.common.data.response;

import com.mineplex.service.common.data.main.MineplexAccount;

import java.util.List;

public record MineplexPagedAccounts(int page,
                                    int perPage,

                                    List<MineplexAccount> results) {
}
