package uz.ruya.mobile.core.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.ruya.mobile.core.config.core.Lang;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class CoreUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Collection<?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isPresent(String str) {
        return StringUtils.hasText(str);
    }

    public static boolean isPresent(Object obj) {
        return obj != null;
    }

    public static boolean isPresent(Collection<?> col) {
        return col != null && !col.isEmpty();
    }

    public static boolean isPresent(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static String generateSmsCode() {
        return String.valueOf((int) (Math.random() * ((999999 - 100000) + 1)) + 100000).substring(0, 6);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static UUID generateTokenUUID() {
        return UUID.randomUUID();
    }

    public static String generateStan() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public static Boolean checkPassword(String password) {
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }
        if (!password.matches("(.*[0-9].*)")) {
            return false;
        }
        return password.matches("(.*[a-z]*)") || password.matches("(.*[A-Z]*)");
    }

    public static String generateVisaSRN() {

        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        String day = dayOfYear > 99 ? dayOfYear + "" : "0" + dayOfYear;

        String todayAsString = new SimpleDateFormat("yyyy:MM:dd").format(new Date());

        Random random = new Random();

        return todayAsString.charAt(3) +
                day +
                83 +
                random.nextInt(9) +
                random.nextInt(9) +
                random.nextInt(9) +
                random.nextInt(9) +
                random.nextInt(9) +
                random.nextInt(9);
    }

    public static boolean checkCardExpired(String expire) {
        Integer year = Integer.valueOf("20" + expire.substring(0, 2));
        int month = Integer.parseInt(expire.substring(2));

        Integer nowYear = LocalDateTime.now().getYear();
        int nowMonth = LocalDateTime.now().getMonth().getValue();

        if (year < nowYear) {
            return true;
        }
        if (year.equals(nowYear)) {
            return month < nowMonth;
        }
        return false;
    }

    public static String formatCardExpire(String expire) {
        return expire.substring(2) + expire.substring(0, 2);
    }

    public static String maskedPhone(String phone) {
        if (isPresent(phone) && phone.length() == 13) {
            return String.format(
                    "%s *** ** %s",
                    phone.substring(1, 5),
                    phone.substring(11)
            );
        } else if (isPresent(phone) && phone.length() == 12) {
            return String.format(
                    "%s *** ** %s",
                    phone.substring(0, 5),
                    phone.substring(10)
            );
        } else {
            return "";
        }
    }

    public static String maskedEmail(String email) {
        String start = email.substring(0, email.indexOf("@"));
        String end = email.substring(email.lastIndexOf("."));
        return start + "***" + end;
    }

    public static String maskedPinfl(String pinfl) {
        if (isPresent(pinfl) && pinfl.length() == 14) {
            return String.format(
                    "%s******%s",
                    pinfl.substring(0, 6),
                    pinfl.substring(12)
            );
        } else {
            return "";
        }
    }

    public static String maskedPan(String pan) {
        if (isPresent(pan) && pan.length() == 16) {
            return String.format(
                    "%s %s** **** **%s",
                    pan.substring(0, 4),
                    pan.substring(4, 6),
                    pan.substring(14)
            );
        } else {
            return "";
        }
    }

    public static String maskedPanForInsuranceFile(String pan) {
        if (isPresent(pan) && pan.length() == 16) {
            return String.format(
                    "%s %s **** %s",
                    pan.substring(0, 4),
                    pan.substring(4, 8),
                    pan.substring(12)
            );
        } else {
            return "";
        }
    }

    public static String reverseMaskedPan(String pan) {
        if (isPresent(pan) && pan.length() == 16) {
            return String.format(
                    "**** **%s %s %s**",
                    pan.substring(6, 8),
                    pan.substring(8, 12),
                    pan.substring(12, 14)
            );
        } else {
            return "";
        }
    }

    public static String maskMoneyFormat(Long amount) {
        if (isEmpty(amount) || amount == 0L) return "0";
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return amount.toString().length() > 2 ? decimalFormat.format(amount / 100d) : ("0" + decimalFormat.format(amount / 100d));
    }

    public static String maskMoneyFormatWithTiyn(Long amount) {
        if (isEmpty(amount) || amount == 0L) return "0";
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.00");
        return amount.toString().length() > 2 ? decimalFormat.format(amount / 100d) : ("0" + decimalFormat.format(amount / 100d));
    }

    public static String maskMoneyForMoneyToWord(Long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("###.00");
        return amount.toString().length() > 2 ? decimalFormat.format(amount / 100d) : ("0" + decimalFormat.format(amount / 100d));
    }

    public static String maskMoneyToDouble(Long amount) {
        return amount.toString().length() > 2 ? String.valueOf(amount / 100d) : "0";
    }

    public static String getMustUpdateMessage(Lang lang) {
        if (Lang.RUS.equals(lang)) {
            return "Пожалуйста, обновите приложение до последней версии";
        } else if (Lang.ENG.equals(lang)) {
            return "Please, update app with latest version";
        }
        return "Iltimos, ilovani oxirgi versiyasini yuklab oling";
    }

    public static String extractVersion(String appVersion) {
        return appVersion.split("[(\\-]")[0].trim();
    }

}
