package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.CategoryType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<AnnouncementParameter> params = new ArrayList<>();

}
