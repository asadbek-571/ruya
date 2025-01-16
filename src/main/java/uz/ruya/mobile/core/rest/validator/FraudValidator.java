package uz.ruya.mobile.core.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ruya.mobile.core.config.excaption.ExternalServiceException;
import uz.ruya.mobile.core.config.excaption.FraudClientServiceException;
import uz.ruya.mobile.core.message.MessageSingleton;


@Component
@RequiredArgsConstructor
public class FraudValidator {

    private final MessageSingleton messageSingleton;

    public boolean isFraudPhoneCheck(boolean checkEnabled, String username, String deviceId, String deviceModel) throws ExternalServiceException, FraudClientServiceException {
        return Boolean.TRUE;
    }

    public void isFraudCodeCheck(boolean checkEnabled, String deviceId, String deviceModel) throws ExternalServiceException, FraudClientServiceException {
    }

    public void refreshFraudCodeCheck(boolean checkEnabled, String deviceId, String deviceModel) {
    }

    public boolean isFraudCodeResendCheck(boolean checkEnabled, String username, String deviceId, String deviceModel) throws ExternalServiceException, FraudClientServiceException {
        return Boolean.FALSE;
    }

    public void isFraudSingIn(boolean checkEnabled, String username, String userId, String deviceId, String deviceModel) throws ExternalServiceException, FraudClientServiceException {
    }

    public void refreshFraudSignIn(boolean checkEnabled, String username, String userId, String deviceId, String deviceModel) {
    }

}
