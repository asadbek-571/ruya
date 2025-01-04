package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.rest.entity.user.UserProfile;

import java.util.UUID;

public interface UserService {
    UserProfile getProfile(UUID userUUID, String username, String email);
}
