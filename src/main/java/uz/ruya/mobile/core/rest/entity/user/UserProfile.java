package uz.ruya.mobile.core.rest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntity;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.application.Application;
import uz.ruya.mobile.core.rest.enums.BaseStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile", schema = BaseScheme.CORE,
        uniqueConstraints = {
                @UniqueConstraint(name = "user_profile_uuid_unique", columnNames = {"uuid"})
        }
)
public class UserProfile extends BaseEntity {

    @Transient
    private final String seqName = "user_profile_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = seqName)
    @SequenceGenerator(name = seqName, sequenceName = seqName, allocationSize = 1)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "is_enable_email", columnDefinition = "boolean default false")
    private Boolean isEnableEmail = Boolean.FALSE;

    @Column(name = "birth_date_str")
    private String birthDateStr;

    @Column(name = "birth_place_str")
    private String birthPlaceStr;

    @Column(name = "is_alert")
    private Boolean isAlert;

    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(name = "avatar_id")
    private UUID avatarId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BaseStatus status = BaseStatus.ACTIVE;

    @Column(name = "blocked_cause")
    private String blockedCause;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Application> applications = new ArrayList<>();

    public Boolean getIsEnableEmail() {
        return CoreUtils.isPresent(isEnableEmail) && isEnableEmail;
    }

    public BaseStatus getStatus() {
        return CoreUtils.isPresent(status) ? status : BaseStatus.ACTIVE;
    }

}
