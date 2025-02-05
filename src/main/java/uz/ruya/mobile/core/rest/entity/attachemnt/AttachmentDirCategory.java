package uz.ruya.mobile.core.rest.entity.attachemnt;

import lombok.*;
import uz.ruya.mobile.core.base.BaseEntity;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.DirType;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachment_dir_category", schema = BaseScheme.FILE)
public class AttachmentDirCategory extends BaseEntityLong {

    @Column(unique = true, nullable = false)
    private String dir;

    @Enumerated(EnumType.STRING)
    private DirType dirType;

}
