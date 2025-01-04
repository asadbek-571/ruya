package uz.ruya.mobile.core.rest.entity.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_category", schema = BaseScheme.INFO)
public class JobCategory extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Column(name = "qty")
    private Integer qty;

    @OneToMany(mappedBy = "jobCategory")
    private List<JobCategoryTranslate> translate = new ArrayList<>();

}
