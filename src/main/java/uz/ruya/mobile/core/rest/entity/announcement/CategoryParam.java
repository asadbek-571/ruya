package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.utils.CoreUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_param", schema = BaseScheme.CORE)
public class CategoryParam extends BaseEntityLong {

    @Column(name = "label")
    private String label;

    @Column(name = "icon")
    private String icon;

    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "max_photos")
    private Integer maxPhotos;

    @Column(name = "order_id")
    private Integer order;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "is_main_parent")
    private Boolean isMainParent = Boolean.FALSE;

    @Column(name = "is_have_unist")
    private Boolean isHaveUnist = Boolean.FALSE;

    @Column(name = "is_unist")
    private Boolean isUnit = Boolean.FALSE;

    @Column(name = "range")
    private Boolean range = Boolean.FALSE;

    @Column(name = "is_required")
    private Boolean isRequired = Boolean.FALSE;

    @Column(name = "min")
    private Integer min;

    @Column(name = "max")
    private Integer max;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "categoryParam")
    private List<CategoryParamTranslate> translate = new ArrayList<>();


    public String getLabelTranslate() {
        if (CoreUtils.isPresent(this.getTranslate())) {

            Optional<CategoryParamTranslate> optional = this.getTranslate().stream()
                    .filter(translate -> GlobalVar.getLANG().equals(translate.getLang())).findFirst();

            if (optional.isPresent()) {
                return optional.get().getName();
            }
        }
        return this.label;
    }

}
