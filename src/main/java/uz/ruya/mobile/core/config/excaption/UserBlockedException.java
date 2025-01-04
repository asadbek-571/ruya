package uz.ruya.mobile.core.config.excaption;

import lombok.Getter;

@Getter
public class UserBlockedException extends Exception {

    private final Integer code;

    public UserBlockedException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
