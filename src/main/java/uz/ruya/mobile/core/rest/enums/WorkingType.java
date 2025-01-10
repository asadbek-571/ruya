package uz.ruya.mobile.core.rest.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingType {
    FULL_DAY("Full Day", "フルタイム", "09:00", "18:00"),        // To‘liq kun: 09:00 - 18:00
    PART_TIME("Part Time", "パートタイム", "09:00", "13:00"),      // Yarim kun: 09:00 - 13:00
    SHIFT_WORK("Shift Work", "シフト制", "Varies", "Varies"),     // Smenali ish
    REMOTE("Remote Work", "リモートワーク", "Flexible", "Flexible"), // Masofaviy ish
    FLEXIBLE("Flexible Hours", "フレキシブル", "Flexible", "Flexible"); // Moslashuvchan

    private final String engName;
    private final String jpName;
    private final String startTime;
    private final String endTime;
}