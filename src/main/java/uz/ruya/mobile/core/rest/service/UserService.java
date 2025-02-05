package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.peyload.res.user.ResUserCvList;

import java.util.UUID;

public interface UserService {
    UserProfile getProfile(UUID userUUID, String username, String email);

    SuccessMessage uploadCv(UUID attachmentId, Boolean isMainCv);

    ResUserCvList cvList();

    SuccessMessage addDescription(String description);

    SuccessMessage toggleSkill(Long skillId);
}
