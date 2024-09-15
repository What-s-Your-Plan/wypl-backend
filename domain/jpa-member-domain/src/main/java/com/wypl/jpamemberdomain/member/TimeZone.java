package com.wypl.jpamemberdomain.member;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeZone {
    KOREA(java.util.TimeZone.getTimeZone("Asia/Seoul")),
    WEST_USA(java.util.TimeZone.getTimeZone("America/Los_Angeles")),
    EAST_USA(java.util.TimeZone.getTimeZone("America/New_York")),
    ENGLAND(java.util.TimeZone.getTimeZone("Europe/London"));

    private final java.util.TimeZone timeZone;

    public static List<TimeZone> getTimeZones() {
        return Arrays.stream(TimeZone.values()).toList();
    }
}
