package uz.ruya.mobile.core.rest.entity.popupAlert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "popup_alert_translate", schema = BaseScheme.CORE)
public class PopupAlertTranslate extends BaseEntityLong {

    @Column(name = "key")
    private Integer key;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Lang lang;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private PopupAlert popupAlert;

}
