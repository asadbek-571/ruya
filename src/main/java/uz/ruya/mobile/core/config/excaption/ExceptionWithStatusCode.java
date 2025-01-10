package uz.ruya.mobile.core.config.excaption;


public class ExceptionWithStatusCode extends Exception {

    private Integer httpStatusCode;

    private String errorMessageKey;
    private boolean translate = false;

    public ExceptionWithStatusCode(Integer httpStatusCode, String errorMessageKey) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessageKey = errorMessageKey;
    }

    public ExceptionWithStatusCode(Integer httpStatusCode, String errorMessageKey, boolean translate) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessageKey = errorMessageKey;
        this.translate = translate;
    }

    public ExceptionWithStatusCode(Integer httpStatusCode, String errorMessageKey, Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
        this.errorMessageKey = errorMessageKey;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public ExceptionWithStatusCode setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public ExceptionWithStatusCode setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
        return this;
    }

    public boolean isTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }
}

