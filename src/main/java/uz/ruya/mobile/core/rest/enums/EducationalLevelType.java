package uz.ruya.mobile.core.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 Asadbek Kushakov 1/8/2025 2:25 PM 
 */

@Getter
@AllArgsConstructor
public enum EducationalLevelType {
    PRIMARY("Primary School", "小学校"),          // Boshlang'ich maktab
    SECONDARY("Secondary School", "中学校"),      // O‘rta maktab
    HIGH_SCHOOL("High School", "高校"),           // Litsey yoki kollej
    BACHELORS("Bachelor's Degree", "学士"),       // Bakalavr
    MASTERS("Master's Degree", "修士"),           // Magistratura
    DOCTORATE("Doctorate/PhD", "博士"),           // Doktorantura
    POST_DOCTORATE("Post Doctorate", "博士後"),   // Postdoktorantura
    OTHER("Other", "その他");                    // Boshqa

    private final String eng;
    private final String jp;
}
