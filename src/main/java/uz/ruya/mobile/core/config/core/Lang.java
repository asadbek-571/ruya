package uz.ruya.mobile.core.config.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Lang {
    UZB("Uzbek", "uz"),
    ENG("English", "en"),
    JP("Japanese", "ja"),
    RUS("Russian", "ru"),
    FRA("French", "fr"),
    GER("German", "de"),
    SPA("Spanish", "es"),
    ITA("Italian", "it"),
    CHI("Chinese", "zh"),
    KOR("Korean", "ko"),
    TUR("Turkish", "tr"),
    HIN("Hindi", "hi"),
    ARA("Arabic", "ar"),
    POR("Portuguese", "pt"),
    SWE("Swedish", "sv"),
    DUT("Dutch", "nl"),
    POL("Polish", "pl"),
    NOR("Norwegian", "no"),
    DAN("Danish", "da"),
    FIN("Finnish", "fi"),
    GRE("Greek", "el"),
    THA("Thai", "th"),
    VIET("Vietnamese", "vi"),
    IND("Indonesian", "id"),
    HEB("Hebrew", "he"),
    MAL("Malay", "ms"),
    CZE("Czech", "cs"),
    HUN("Hungarian", "hu");

    private final String fullName;
    private final String code;

    public static Lang getByName(final String name) {
        return Arrays.stream(Lang.values())
                .filter(lang -> lang.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UZB);
    }

    public static Lang getByCode(final String code) {
        return Arrays.stream(Lang.values())
                .filter(lang -> lang.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(UZB);
    }
}
