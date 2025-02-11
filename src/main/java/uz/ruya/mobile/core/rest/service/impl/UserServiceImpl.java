package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqSpecialization;
import uz.ruya.mobile.core.rest.repo.user.UserProfileRepo;
import uz.ruya.mobile.core.rest.service.UserService;

import java.util.Optional;
import java.util.UUID;

/**
 Asadbek Kushakov 12/25/2024 10:46 AM 
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepo profileRepo;
    private final MessageSingleton messageSingleton;

    @Override
    public UserProfile getProfile(UUID userUUID, String username, String email) {

        Optional<UserProfile> profileOptional = profileRepo.findUserProfileByUuid(userUUID);

        UserProfile profile;
        if (profileOptional.isEmpty()) {
            profile = new UserProfile();
            profile.setEmail(email);
            profile.setUuid(userUUID);
            profile.setStatus(BaseStatus.ACTIVE);
            profile = profileRepo.saveAndFlush(profile);
        } else {
            profile = profileOptional.get();
        }

        return profile;
    }

    @Override
    public SuccessMessage attachAvatar(ReqSpecialization request) {
        return null;
    }

}
