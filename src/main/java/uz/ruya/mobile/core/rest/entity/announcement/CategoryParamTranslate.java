package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_param", schema = BaseScheme.CORE)
public class CategoryParamTranslate extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Lang lang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_param_id")
    private CategoryParam categoryParam;

}
