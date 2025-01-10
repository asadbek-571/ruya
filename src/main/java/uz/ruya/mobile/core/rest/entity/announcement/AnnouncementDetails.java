package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.enums.JobType;
import uz.ruya.mobile.core.rest.enums.WorkingType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement_details", schema = BaseScheme.CORE)
public class AnnouncementDetails extends BaseEntityLong {

    @OneToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "working_type")
    private WorkingType workingType;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency")
    private CurrencyType currency;

}