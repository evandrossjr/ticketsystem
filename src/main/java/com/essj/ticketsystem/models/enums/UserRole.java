package com.essj.ticketsystem.models.enums;

public class UserRole {
    public enum RoleName {
        USER,
        ADMIN,
        SUPPORT_AGENT
    }

    private RoleName roleName;

    public UserRole(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }




}
