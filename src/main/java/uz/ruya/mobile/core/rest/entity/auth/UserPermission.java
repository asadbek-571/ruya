package uz.ruya.mobile.core.rest.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityUUID;
import uz.ruya.mobile.core.base.BaseScheme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_permission", schema = BaseScheme.AUTH,
        uniqueConstraints = {
                @UniqueConstraint(name = "user_permission_name_unique", columnNames = {"name"})
        }
)
public class UserPermission extends BaseEntityUUID {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

}
