package uz.ruya.mobile.core.config.core;

import java.util.Arrays;

public enum DevMode {
    DEFAULT,
    DEV,
    TG,
    RELEASE;

    public static DevMode getByName(final String name) {
        return Arrays.stream(DevMode.values())
                .filter(devMode -> devMode.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(RELEASE);
    }
}
