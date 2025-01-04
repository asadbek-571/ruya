package uz.ruya.mobile.core.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {

    USD("United States Dollar"),
    EUR("Euro"),
    UZS("Uzbekistani Som"),
    JPY("Japanese Yen"),
    GBP("British Pound Sterling"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan"),
    RUB("Russian Ruble");

    private final String fullName;

}
