package uz.ruya.mobile.core.config.excaption;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}