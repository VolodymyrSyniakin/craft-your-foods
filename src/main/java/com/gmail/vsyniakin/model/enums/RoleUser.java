package com.gmail.vsyniakin.model.enums;

public enum RoleUser {
    ADMIN, USER, MODERATOR;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
