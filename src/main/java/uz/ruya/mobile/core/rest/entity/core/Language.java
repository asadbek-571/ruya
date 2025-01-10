package uz.ruya.mobile.core.rest.entity.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;

import javax.persistence.*;

/**
 Asadbek Kushakov 1/8/2025 2:52 PM 
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "language", schema = BaseScheme.INFO)
public class Language extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Lang lang;
}
