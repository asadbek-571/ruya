package uz.ruya.mobile.core.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {

    USD("$", "United States Dollar", "USD", 840),
    UZS("so'm", "Uzbek so'm", "UZS", 860);
//    EUR("€", "Euro", "EUR", 978),
//    JPY("¥", "Japanese Yen", "JPY", 392),
//    GBP("£", "British Pound Sterling", "GBP", 826),
//    AUD("A$", "Australian Dollar", "AUD", 36),
//    CAD("C$", "Canadian Dollar", "CAD", 124),
//    CHF("CHF", "Swiss Franc", "CHF", 756),
//    CNY("¥", "Chinese Yuan", "CNY", 156),
//    RUB("₽", "Russian Ruble", "RUB", 643);

    private final String symbol;
    private final String currencyName;
    private final String currencyCode;
    private final Integer currencyNumber;
}

