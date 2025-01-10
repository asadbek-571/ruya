package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.enums.CategoryType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category", schema = BaseScheme.CORE)
public class Category extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "max_photos")
    private Integer maxPhotos;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CategoryType type;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<CategoryTranslate> translate = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<CategoryParam> params = new ArrayList<>();

    public String getLabelTranslate() {
        if (CoreUtils.isPresent(this.getTranslate())) {

            Optional<CategoryTranslate> optional = this.getTranslate().stream()
                    .filter(translate -> GlobalVar.getLANG().equals(translate.getLang())).findFirst();

            if (optional.isPresent()) {
                return optional.get().getName();
            }
        }
        return this.name;
    }

}
