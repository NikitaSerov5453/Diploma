package com.example.diploma.enums;


import java.util.Arrays;
import java.util.Objects;

public enum RoleType {

    ROLE_ADMIN,
    ROLE_USER,
    ROLE_MODERATOR;

    public static RoleType fromString(String role) {
        return Arrays.stream(RoleType.values())
                .filter(type -> Objects.equals(role, type.name()))
                .findAny()
                .orElse(null);
    }
}
