package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement_parameters", schema = BaseScheme.CORE)
public class AnnouncementParameter extends BaseEntityLong {

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "unit_value")
    private String unitValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    public AnnouncementParameter(ReqAddAnnouncement.ReqAddAnnouncementParam param, Announcement announcement) {
        this.key = param.getCode();
        this.value = param.getValue();
        this.announcement = announcement;
        this.unitValue = param.getUnitValue();
    }
}