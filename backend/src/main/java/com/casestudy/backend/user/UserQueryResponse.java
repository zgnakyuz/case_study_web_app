package com.casestudy.backend.user;

import lombok.Getter;

import java.util.List;


@Getter
public class UserQueryResponse {

    private final Long id;

    private final String username;

    private final String email;

    private final Integer money;

    private final List<String> roles;

    public UserQueryResponse(Long id, String username, String email, Integer money, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.money = money;
        this.roles = roles;
    }
}

