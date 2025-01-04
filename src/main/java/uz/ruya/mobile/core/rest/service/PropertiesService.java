package uz.ruya.mobile.core.rest.service;

public interface PropertiesService {
    Boolean isTechnicalBreak();
    Boolean isCheckAppVersionEnabled();

    String getMinAndroidVersion();

    String getMinIosVersion();
}
