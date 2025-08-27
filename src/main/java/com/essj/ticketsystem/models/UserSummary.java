package com.essj.ticketsystem.models;

public class UserSummary {

    private final String id;
    private final String name;

    public UserSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
