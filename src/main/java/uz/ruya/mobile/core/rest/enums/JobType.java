package uz.ruya.mobile.core.rest.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public enum JobType {

    FULL_TIME("Full-Time", "フルタイム"),
    PART_TIME("Part-Time", "パートタイム"),
    CONTRACT("Contract", "契約"),
    INTERNSHIP("Internship", "インターンシップ"),
    REMOTE("Remote", "リモート");

    private final String eng;
    private final String jp;

}
