package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.properties.PropertiesEntity;
import uz.ruya.mobile.core.rest.repo.properties.PropertiesRepository;
import uz.ruya.mobile.core.rest.service.PropertiesService;

import java.util.Optional;

/**
 Asadbek Kushakov 12/25/2024 10:43 AM 
 */

@Service
@RequiredArgsConstructor
public class PropertiesServiceImpl implements PropertiesService {

    private final PropertiesRepository repository;

    private Boolean getBooleanProperty(String key, Boolean defaultValue) {
        try {
            String value = getPropertyValue(key);
            return CoreUtils.isPresent(value) ? Integer.parseInt(value) == 1 : defaultValue;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private Long getLongProperty(String key, Long defaultValue) {
        try {
            String value = getPropertyValue(key);
            return CoreUtils.isPresent(value) ? Long.parseLong(value) : defaultValue;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private Integer getIntegerProperty(String key, Integer defaultValue) {
        try {
            String value = getPropertyValue(key);
            return CoreUtils.isPresent(value) ? Integer.parseInt(value) : defaultValue;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private Double getDoubleProperty(String key, Double defaultValue) {
        try {
            String value = getPropertyValue(key);
            return CoreUtils.isPresent(value) ? Double.parseDouble(value) : defaultValue;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private String getStringProperty(String key, String defaultValue) {
        try {
            String value = getPropertyValue(key);
            return CoreUtils.isPresent(value) ? value : defaultValue;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private String getPropertyValue(String key) {
        Optional<PropertiesEntity> propertyOptional = repository.findByKey(key);
        return propertyOptional.map(PropertiesEntity::getValue).orElse(null);
    }

    @Override
    public Boolean isTechnicalBreak() {
        return getBooleanProperty("is_technical_break", false);
    }

    @Override
    public Boolean isCheckAppVersionEnabled() {
        return getBooleanProperty("is_check_app_version_enabled", false);
    }

    @Override
    public String getMinAndroidVersion() {
        return getStringProperty("min_version_android", "");
    }

    @Override
    public String getMinIosVersion() {
        return getStringProperty("min_version_ios", "");
    }

    @Override
    public Boolean isFraudPhoneCheck() {
        return getBooleanProperty("is_fraud_phone_check", false);
    }

    @Override
    public Boolean isFraudCodeCheck() {
        return getBooleanProperty("is_fraud_code_check", false);
    }

    @Override
    public Boolean isFraudSignIn() {
        return getBooleanProperty("is_fraud_sign_in", false);
    }

    @Override
    public Long getAccessTokenExpireHours() {
        if (DeviceType.ANDROID.equals(GlobalVar.getDEVICE_TYPE())) {
            return getLongProperty("access_token_android_expire_hours", 2L);
        } else if (DeviceType.IOS.equals(GlobalVar.getDEVICE_TYPE())) {
            return getLongProperty("access_token_ios_expire_hours", 2L);
        } else if (DeviceType.WEB.equals(GlobalVar.getDEVICE_TYPE())) {
            return getLongProperty("access_token_web_expire_hours", 2L);
        }
        return 2L;
    }

    @Override
    public Long getAccessTokenExpireHours(String username) {
        if (DeviceType.ANDROID.equals(GlobalVar.getDEVICE_TYPE())) {
            return 2L;
        } else if (DeviceType.IOS.equals(GlobalVar.getDEVICE_TYPE())) {
            return 2L;
        } else if (DeviceType.WEB.equals(GlobalVar.getDEVICE_TYPE())) {
            return 2L;
        }
        return 2L;
    }

    @Override
    public Boolean isFraudCodeResendCheck() {
        return getBooleanProperty("is_fraud_code_resend_check", false);
    }


    @Override
    public String getPolicyUrlForRegistration() {
        return getStringProperty("policy_url_for_registration", "https://file.ruya.uz/policy.html");
    }
}
