package uz.ruya.mobile.core.config.excaption;

import lombok.Getter;

@Getter
public class InvalidTokenException extends Exception {

    private final Integer code;

    public InvalidTokenException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
