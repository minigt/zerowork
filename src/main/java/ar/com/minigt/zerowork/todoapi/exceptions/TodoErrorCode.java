package ar.com.minigt.zerowork.todoapi.exceptions;

public enum TodoErrorCode {

    INVALID_REQUEST(400, 4000, "Invalid request"),
    TITTLE_NOT_NULL(400, 4001, "Tittle can't be null"),
    TITTLE_NOT_EMPTY(400, 4002, "Tittle can't be empty"),
    DESCRIPTION_NOT_NULL(400, 4003, "Description can't be null"),
    DESCRIPTION_NOT_EMPTY(400, 4004, "Description can't be empty"),
    STATE_NOT_NULL(400, 4005, "State can't be null"),
    INVALID_STATE(400, 4007, "State is not valid"),
    ;

    private final int code;
    private final int extendedCode;
    private final String reason;

    TodoErrorCode(int statusCode, int extendedCode, String reason) {
        this.code = statusCode;
        this.extendedCode = extendedCode;
        this.reason = reason;
    }

    public int getStatusCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reason;
    }

    public Integer getExtendedCode() {
        return extendedCode;
    }

    public static TodoErrorCode getByExtendedCode(Integer extendedCode) {
        for (TodoErrorCode error : TodoErrorCode.values()) {
            if (error.getExtendedCode().equals(extendedCode)) {
                return error;
            }
        }
        return null;
    }
}
