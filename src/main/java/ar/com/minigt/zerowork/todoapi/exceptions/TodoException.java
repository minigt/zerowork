package ar.com.minigt.zerowork.todoapi.exceptions;

public class TodoException extends RuntimeException {

    private final int statusCode;
    private final int extendedCode;
    private final String reason;

    public TodoException(TodoErrorCode errorCode) {
        super(errorCode.getReasonPhrase());
        this.statusCode = errorCode.getStatusCode();
        this.extendedCode = errorCode.getExtendedCode();
        this.reason = errorCode.getReasonPhrase();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getExtendedCode() {
        return extendedCode;
    }

    public String getReason() {
        return reason;
    }
}
