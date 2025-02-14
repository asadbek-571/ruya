package uz.ruya.mobile.core.config.excaption;

import lombok.Getter;

@Getter
public class InvalidUserException extends Exception {
    private final Integer code;

    public InvalidUserException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
