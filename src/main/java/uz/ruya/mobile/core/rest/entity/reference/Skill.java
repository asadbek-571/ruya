package uz.ruya.mobile.core.rest.entity.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.entity.user.UserSkill;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skills", schema = BaseScheme.CORE)
public class Skill extends BaseEntityLong {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "skill_id")
    private List<UserSkill> skills = new ArrayList<>();
}
