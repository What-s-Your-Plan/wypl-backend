package com.wypl.jpamemberdomain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OauthProvider {
    GOOGLE("gmail.com");

    private final String domain;

    public boolean equalsName(final String otherName) {
        return name().equalsIgnoreCase(otherName);
    }
}
