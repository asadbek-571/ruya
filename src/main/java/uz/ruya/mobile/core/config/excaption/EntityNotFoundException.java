package uz.ruya.mobile.core.config.excaption;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
        this("Could not find entity with given criteria.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
