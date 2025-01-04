package uz.ruya.mobile.core.rest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityUUID;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role", schema = BaseScheme.AUTH,
        uniqueConstraints = {
                @UniqueConstraint(name = "user_role_name_unique", columnNames = {"name"})
        }
)
public class UserRole extends BaseEntityUUID {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType type = UserRoleType.DEMO;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles_permissions", schema = BaseScheme.AUTH,
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<UserPermission> userPermissions;

    @Column(name = "name_key", nullable = false, updatable = false)
    private UUID nameKey = UUID.randomUUID();

}
