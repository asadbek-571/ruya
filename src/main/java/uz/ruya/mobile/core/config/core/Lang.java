package uz.ruya.mobile.core.config.core;

import java.util.Arrays;

public enum Lang {
    RUS,
    UZB,
    ENG,
    KAA,
    KRL;

    public static Lang getByName(final String name) {
        return Arrays.stream(Lang.values())
                .filter(lang -> lang.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UZB);
    }
}
