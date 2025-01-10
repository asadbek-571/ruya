package uz.ruya.mobile.core.config.excaption;

import lombok.Getter;

@Getter
public class UserBlockedException extends Exception {

    private Integer code;

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
