package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.enums.CurrencyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement", schema = BaseScheme.CORE)
public class Announcement extends BaseEntityLong {

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "address_long")
    private String addressLong;

    @Column(name = "address_lat")
    private String addressLat;

    @Column(name = "applied_qty")
    private Integer appliedQty;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AnnouncementType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    @JoinColumn(name = "announcement_id")
    private List<AnnouncementParameter> parameters = new ArrayList<>();
}
