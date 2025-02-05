package uz.ruya.mobile.core.rest.entity.attachemnt;

import lombok.*;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.DirType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments", schema = BaseScheme.FILE)
public class Attachment extends BaseEntityLong {

    @Column(name = "uuid")
    protected UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "full_path", nullable = false)
    private String fullPath;

    @Enumerated(EnumType.STRING)
    private BaseStatus baseStatus;

    @Column(name = "dir_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DirType dirType;

    @Column(name = "url_name")
    private String urlName;

}
