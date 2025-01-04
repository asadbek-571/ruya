package uz.ruya.mobile.core.config.core;

import java.util.Arrays;

public enum DeviceType {
    ANDROID,
    IOS,
    WEB,
    UNKNOWN;

    public static DeviceType getByName(final String name) {
        return Arrays.stream(DeviceType.values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
