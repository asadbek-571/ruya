package uz.ruya.mobile.core.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExperienceLevel {

    ZERO_EXPERIENCE("0 Years", "0年"),
    ONE_TO_THREE_YEARS("1 to 3 Years", "1年から3年"),
    THREE_TO_SIX_YEARS("3 to 6 Years", "3年から6年"),
    SIX_TO_TEN_YEARS("6 to 10 Years", "6年から10年"),
    TEN_PLUS_YEARS("10+ Years", "10年以上");

    private final String eng;
    private final String jp;

}
