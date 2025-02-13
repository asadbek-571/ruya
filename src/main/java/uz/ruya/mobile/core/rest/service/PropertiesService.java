package uz.ruya.mobile.core.rest.service;

public interface PropertiesService {

    Boolean isTechnicalBreak();

    Boolean isCheckAppVersionEnabled();

    String getMinAndroidVersion();

    String getMinIosVersion();

    Boolean isFraudPhoneCheck();

    Boolean isFraudCodeCheck();

    Boolean isFraudSignIn();

    Boolean isFraudCodeResendCheck();

    Long getAccessTokenExpireHours();

    Long getAccessTokenExpireHours(String username);

    String getPolicyUrlForRegistration();

    String getBotToken();
}
