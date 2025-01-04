package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.properties.PropertiesEntity;
import uz.ruya.mobile.core.rest.repo.PropertiesRepository;
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
}
