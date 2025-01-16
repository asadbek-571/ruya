package uz.ruya.mobile.core.config.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ruya.mobile.core.rest.service.PropertiesService;

@Component
@RequiredArgsConstructor
public class NotifyUtils {

    private final PropertiesService propertiesService;

    public String getChangePasswordMessage(String smsCode) {
        return String.format(
                "Ruya sms xizmati. Ushbu kodni hech kimga bermang! Firibgarlardan ehtiyot bo'ling! Ruya tizimidagi parol o'zgartirilmoqda. Kod: %s. %s",
                smsCode,
                "aSFEVggf"
        );
    }

    public String getChangePasswordMessageEmail() {
        return "Sizning akkountingiz parolida o'zgarish bo'ldi. Ilova parolini hech kimga bermang. Parolni faqat firibgarlar so'rashi mumkin.";
    }

    public String getLoginByQrScanMessage(String smsCode) {
        return String.format(
                "Ruya sms xizmati. Ushbu kodni hech kimga bermang! Firibgarlardan ehtiyot bo'ling! Ruya tizimiga kirilmoqda. Kod: %s.",
                smsCode
        );
    }

    public String getWebSignMessage(String date, String ipAddress) {
        return String.format(
                "DIQQAT! Yangi qurilmadan akkountingizga kirishga urinishmoqda. %s, Brouzerdan kirilmoqda. IP %s",
                date,
                ipAddress
        );
    }

    public String getSignMessage(String smsCode) {
        return String.format(
                "Ruya sms xizmati. Ushbu kodni hech kimga bermang! Firibgarlardan ehtiyot bo'ling! Ruya tizimiga kirilmoqda. Kod: %s. %s",
                smsCode,
                "sdRgFknfs"
        );
    }

}
