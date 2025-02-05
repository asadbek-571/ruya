package uz.ruya.mobile.core.rest.repo.attachment;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.attachemnt.AttachmentUser;
import uz.ruya.mobile.core.rest.enums.UserAttachType;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentUserRepo extends BaseRepositoryLong<AttachmentUser> {
    List<AttachmentUser> findAllByTypeAndUserId(UserAttachType type, UUID userId);

}