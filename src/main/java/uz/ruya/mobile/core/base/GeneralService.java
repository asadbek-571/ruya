package uz.ruya.mobile.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GeneralService {

    UNKNOWN("default"),

    UZCARD("uzcard"),
    UZCARD_MASTERCARD("mastercard"),
    UZCARD_MIR("mir"),
    UZCARD_UNIONPAY("unionpay"),
    UZCARD_DUO("uzcard"),

    HUMO("humo"),
    HUMO_VISA("visa"),
    HUMO_MASTERCARD("mastercard"),

    VISA("visa"),
    VISA_UZS("visa"),

    MASTERCARD("mastercard"),

    WALLET("wallet"),

    UZASBO_STUDENT("uzasbo_student"),
    UZASBO_CHILD("uzasbo_child"),
    INPS("inps"),
    INPS_OUTCOME("inps_outcome"),
    INTERNATIONAL_EXAM("international_exam"),
    SUYUNCHI("suyunchi"),
    FUNERAL("funeral"),
    PAYNET("paynet"),
    INSON_INSURANCE("inson_insurance"),
    TEZ_QR("tez_qr"),
    AVIA_TICKET("default"),
    AUTO_ATTORNEY("default"),
    ACCOUNT("default"),
    NASIYA("default"),
    MERCHANT("default"),
    KATM("default"),
    KATM_SMS("default"),
    KATM_FREEZE("default"),

    LOAN_PRODUCT("default"),
    LOAN_PAYMENT("default"),
    LOAN_EARLY_PAYMENT("default"),

    DEPOSIT_OPEN("default"),
    DEPOSIT_CLOSE("default"),
    DEPOSIT_EARLY_CLOSE("default"),
    DEPOSIT_INCREASE("default"),
    DEPOSIT_DECREASE("default"),
    DEPOSIT_PARTIAL_WITHDRAW("default"),

    VISA_CONVERSATION_CARD("default"),
    CARD_CONVERSATION_VISA("default"),
    WALLET_USD_CONVERSATION_CARD("default"),
    WALLET_EUR_CONVERSATION_CARD("default")

    ;

    private final String logoName;

    public static GeneralService getByName(final String name) {
        return Arrays.stream(GeneralService.values())
                .filter(service -> service.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
