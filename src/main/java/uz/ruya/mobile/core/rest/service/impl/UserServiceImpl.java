package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.auth.AuthUser;
import uz.ruya.mobile.core.auth.UserDBO;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.auth.User;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqEditProfile;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqSpecialization;
import uz.ruya.mobile.core.rest.peyload.res.user.ResUser;
import uz.ruya.mobile.core.rest.repo.auth.UserRepo;
import uz.ruya.mobile.core.rest.repo.user.UserProfileRepo;
import uz.ruya.mobile.core.rest.service.UserService;

import javax.persistence.EntityNotFoundException;
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
    private final UserRepo userRepo;

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
        return new SuccessMessage();
    }

    @Override
    public ResUser me() {
        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();
        UserDBO user = authUser.getUser().getUser();
        return new ResUser(profile, user);
    }

    @Override
    public SuccessMessage editProfile(ReqEditProfile request) {
        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();
        profile.setEmail(request.getEmail());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profileRepo.saveAndFlush(profile);

        User user = userRepo.findById(profile.getUuid()).orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.USER_NOT_FOUND)));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepo.saveAndFlush(user);

        return new SuccessMessage(messageSingleton.getMessage(MessageKey.SUCCESS));
    }

}
