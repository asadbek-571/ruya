package uz.ruya.mobile.core.rest.entity.attachemnt;

import lombok.*;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.UserAttachType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachment_user", schema = BaseScheme.FILE)
public class AttachmentUser extends BaseEntityLong {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "attachment_id")
    private UUID attachmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserAttachType type;

}
