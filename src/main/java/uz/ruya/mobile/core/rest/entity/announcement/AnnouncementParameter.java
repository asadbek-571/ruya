package uz.ruya.mobile.core.rest.entity.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement_parameters", schema = BaseScheme.CORE)
public class AnnouncementParameter extends BaseEntityLong {

    @Column(name = "param_id")
    private Long paramId;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value")
    private String value;

    @ElementCollection
    @CollectionTable(name = "announcement_parameter_values", schema = BaseScheme.CORE, joinColumns = @JoinColumn(name = "announcement_parameter_id"))
    @Column(name = "multi_value")
    private List<String> multiValue = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    public AnnouncementParameter(ReqAddAnnouncement.ReqAddAnnouncementParam param, Announcement announcement) {
        this.paramId = param.getId();
        this.key = param.getCode();
        this.value = param.getValue();
        this.announcement = announcement;
        this.multiValue = param.getMultiValue();
    }
}