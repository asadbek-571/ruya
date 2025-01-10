package uz.ruya.mobile.core.rest.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityUUID;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.UserGender;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = BaseScheme.AUTH)
public class User extends BaseEntityUUID {

    @Transient
    private final String fUserRole = "user_role_fkey";

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_no")
    private String userNO;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BaseStatus status = BaseStatus.BLOCKED;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender = UserGender.UNKNOWN;

    @Column(name = "age")
    private Integer age = 0;

    @Column(name = "pin")
    private String pin;

    @Column(name = "is_premium")
    private Boolean isPremium = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false, foreignKey = @ForeignKey(name = fUserRole))
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserAccess access;

}
