package uz.ruya.mobile.core.config.utils;

import org.springframework.util.StringUtils;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.config.core.Lang;
import uz.ruya.mobile.core.config.excaption.BadRequestException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static DateTimeFormatter fLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter fLocalDateDashed = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter fReceiptTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static DateTimeFormatter fLocalDateDotted = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter fLocalTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter fLocalDateTimeWithT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static DateTimeFormatter fLocalDateTimeWithT2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    public static DateTimeFormatter fMonthDate = DateTimeFormatter.ofPattern("MMM, dd, yyyy");

    public static LocalDateTime identityTokenExpire() {
        return LocalDateTime.now().plusMinutes(10L);
    }

    public static LocalDateTime refreshTokenExpire(DeviceType deviceType) {
        if (DeviceType.WEB.equals(deviceType)) {
            return refreshTokenExpireWeb();
        } else {
            return refreshTokenExpire();
        }
    }

    public static LocalDateTime refreshTokenExpire() {
        return LocalDateTime.now().plusYears(1L);
    }

    public static LocalDateTime refreshTokenExpireWeb() {
        return LocalDateTime.now().plusDays(1L);
    }

    public static LocalDateTime emailCodeExpire() {
        return LocalDateTime.now().plusMinutes(5L);
    }

    public static LocalDateTime codeExpire() {
        return LocalDateTime.now().plusMinutes(10L);
    }

    public static String dateTimeToFront(LocalDateTime dateTime) {
        return fLocalDateDotted.format(dateTime);
    }

    public static LocalDate parseDateDotted(String date) {
        return LocalDate.parse(date, fLocalDateDotted);
    }

    public static LocalDate parseDateDashed(String date) {
        return LocalDate.parse(date, fLocalDateDashed);
    }

    public static LocalTime parseTime(String date) {
        return LocalTime.parse(date, fLocalTime);
    }

    public static Long startDate(String date) {
        LocalDate localDate = LocalDate.parse(date, fLocalDateDashed);
        return Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.MIN)).getTime();
    }

    public static Long endDate(String date) {
        LocalDate localDate = LocalDate.parse(date, fLocalDateDashed);
        return Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.MAX)).getTime();
    }

    public static String startDateFormat(String startDate) {
        if (startDate == null || startDate.isBlank() || startDate.isEmpty()) {
            startDate = dateForUzcard(LocalDate.now().minusDays(30));
        }
        return startDate.replaceAll("-", "");
    }

    public static String endDateFormat(String endDate) {
        if (endDate == null || endDate.isBlank() || endDate.isEmpty()) {
            endDate = dateForUzcard(LocalDate.now());
        }
        return endDate.replaceAll("-", "");
    }

    public static String dateForUzcard(LocalDate date) {
        return "" + date.getYear() + date.getMonthValue() + date.getDayOfMonth();
    }

    public static String dateUzcardToFront(Integer date) {
        String dateStr = date.toString();
        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
    }

    public static String timeUzcardToFront(Integer time) {
        String timeStr = time.toString();
        if (timeStr.length() == 6) {
            return timeStr.substring(0, 2) + ":" + timeStr.substring(2, 4) + ":" + timeStr.substring(4);
        } else {
            return timeStr.charAt(0) + ":" + timeStr.substring(1, 3) + ":" + timeStr.substring(3);
        }
    }

    public static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static LocalDateTime getFirstDayYear() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().withDayOfYear(1)), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime getFirstDayMonth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().withDayOfMonth(1)), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime getFirstToday() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now()), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime stringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime stringToLocalDateTime2(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDateTime.of(LocalDate.parse(date, formatter), LocalTime.MAX);
    }

    public static String receiptTime(LocalDateTime date) {
        return fReceiptTime.format(date);
    }

    public static LocalDateTime parseReceiptTime(String date) throws BadRequestException {
        if (StringUtils.hasText(date)) {

            try {
                return LocalDateTime.parse(date, fReceiptTime);
            } catch (Exception e) {
                throw new BadRequestException(", pattern should be like that : \"yyyy-MM-dd HH:mm:ss\" ");
            }
        } else {
            return null;
        }
    }

    public static LocalDateTime stringToLocalDateTime(String date, DateTimeFormatter formatter) {
        return LocalDateTime.parse(date, formatter);
    }

    public static Long dateToMilliseconds(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    public static LocalDate parseLocalDate(String string) throws BadRequestException {
        try {
            return LocalDate.parse(string, fLocalDateDashed);
        } catch (Exception e) {
            throw new BadRequestException(", pattern should be like that : \"yyyy-MM-dd\" ");
        }
    }

    public static LocalDate parseLocalDateDotted(String string) throws BadRequestException {
        try {
            return LocalDate.parse(string, fLocalDateDotted);
        } catch (Exception e) {
            throw new BadRequestException("");
        }
    }

    public static String toLocalDateString(LocalDate localDate) {
        return localDate.format(fLocalDateDashed);
    }


    public static String difference(String start, String end) {
        try {
            LocalDateTime startTime = parseReceiptTime(start);
            LocalDateTime endTime = parseReceiptTime(end);

            assert startTime != null;
            Duration duration = Duration.between(startTime, endTime);
            long days = duration.toDays();
            long hours = duration.minusDays(days).toHoursPart();
            long minutes = duration.minusHours(hours).toMinutesPart();
            long seconds = duration.minusMinutes(minutes).toSecondsPart();

            String result = "";

            if (days != 0f) {
                result += days + " day,";
            }

            if (hours != 0f) {
                result += hours + " hours,";
            }

            if (minutes != 0f) {
                result += minutes + " minutes,";
            }

            if (seconds != 0f) {
                result += seconds + " seconds,";
            }

            return result.substring(0, result.length() - 1);

        } catch (Exception | BadRequestException e) {
            return "";
        }
    }

    public static String isoToDotted(String date) throws ParseException {
        Date parsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        DateFormat europeanFormat = new SimpleDateFormat("dd.MM.yyyy");
        return europeanFormat.format(parsed);
    }

    public static String dashedToDotted(Date date) {
        return fLocalDateDotted.format(date.toInstant());
    }

    public static String dashedToDotted(String date) {
        if (CoreUtils.isPresent(date)) {
            return LocalDate.parse(date).format(DateUtils.fLocalDateDotted);
        } else {
            return "";
        }
    }

    public static String dashedToDotted(LocalDate date) {
        return date.format(DateUtils.fLocalDateDotted);
    }

    public static String dottedToDashed(String date) {
        LocalDate dateD = LocalDate.parse(date, fLocalDateDotted);
        return dateD.format(fLocalDateDashed);
    }

    public static LocalDateTime convertToLocalDateTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }

    public static LocalDateTime convertToLocalDateTime(String dateString) {
        LocalDate date = LocalDate.parse(dateString, fLocalDateDashed);
        return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }

    public static int getDifferenceDays(LocalDate start, LocalDate end) {
        int day = (int) ChronoUnit.DAYS.between(isExpire(start) ? LocalDate.now() : start, end);
        return day > 1 ? day : 0;
    }

    public static int getDifferenceDays(LocalDate end) {
        int day = (int) ChronoUnit.DAYS.between(LocalDate.now(), end);
        return day > 1 ? day : 0;
    }

    public static boolean isExpire(LocalDate expirationDate) {
        return expirationDate.isBefore(LocalDate.now());
    }

    public static boolean isExpire(String date) {
        LocalDate expirationDate = LocalDate.parse(date);
        return expirationDate.isBefore(LocalDate.now());
    }

    public static boolean isDepositCloseDay(String closeDay) {
        LocalDate date1 = LocalDate.parse(closeDay, fLocalDateDotted);
        LocalDate date2 = LocalDate.now();
        return date1.equals(date2) || date1.isBefore(date2);
    }

    public static boolean isNotDepositCloseDay(String closeDay) {
        LocalDate date1 = LocalDate.parse(closeDay, fLocalDateDotted);
        LocalDate date2 = LocalDate.now();
        return date1.isAfter(date2);
    }

    public static Integer calculateAge(String birthDateStr) {
        try {
            LocalDate birthDate = parseLocalDateDotted(birthDateStr);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (Throwable th) {
            return 30;
        }
    }

    public static String monthFormat(LocalDateTime dateTime) {
        return dateTime.format(fMonthDate);
    }

    public static String getDifferanceHoursForApplication(String dateTime, int addedHour, Lang lang) {
        try {
            LocalDateTime waitingTime = DateUtils.stringToLocalDateTime(dateTime, fLocalDateTimeWithT2).plusHours(addedHour);
            Duration duration = Duration.between(LocalDateTime.now(), waitingTime);

            long hours = duration.toHours();

            switch (lang) {
                case UZB:
                    return hours + "s " + duration.minusHours(hours).toMinutes() + " daqiqa";
                case RUS:
                    return hours + "ч " + duration.minusHours(hours).toMinutes() + " мин";
                case ENG:
                    return hours + "h " + duration.minusHours(hours).toMinutes() + " min";

            }


        } catch (Throwable th) {
            return null;
        }

        return null;
    }

    public static String getCurrentTime() {
        return LocalDateTime.now().format(fReceiptTime);
    }

}
