package uz.ruya.mobile.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GeneralParentService {
    UNKNOWN("default"),
    UZCARD("uzcard"),
    HUMO("humo"),
    VISA("visa"),
    MASTERCARD("mastercard"),
    WALLET("wallet"),
    PAYMENT("payment"),
    LOAN("default"),
    DEPOSIT("default"),
    CONVERSATION("default"),
    INSURANCE("default"),
    TICKET("default");

    private final String logoName;

    public static GeneralParentService getByName(final String name) {
        return Arrays.stream(GeneralParentService.values())
                .filter(service -> service.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
