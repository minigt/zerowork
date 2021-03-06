package ar.com.minigt.zerowork.todoapi.exceptions;

public enum TodoErrorCode {

    INVALID_REQUEST(400, 4000, "Invalid request"),
    TITTLE_NOT_NULL(400, 4001, "Tittle can't be null"),
    TITTLE_NOT_EMPTY(400, 4002, "Tittle can't be empty"),
    DESCRIPTION_NOT_NULL(400, 4003, "Description can't be null"),
    DESCRIPTION_NOT_EMPTY(400, 4004, "Description can't be empty"),
    STATE_NOT_NULL(400, 4005, "State can't be null"),
    INVALID_STATE(400, 4006, "State is not valid"),
    TODO_ID_CANT_BE_NULL(400, 4007, "Todo id can't be null"),
    TODO_NOT_FOUND(400, 4008, "Todo not found"),
    INVALID_TODO_ID(400, 4009, "Invalid todo id"),
    TAG_NAME_NOT_NULL(400, 4010, "Name can't be null"),
    TAG_NAME_NOT_EMPTY(400, 4011, "Name can't be empty"),
    TAG_ID_CANT_BE_NULL(400, 4012, "Tag id can't be null"),
    TAG_NOT_FOUND(400, 4013, "Tag not found"),
    INVALID_TAG_ID(400, 4014, "Invalid tag id"),
    INVALID_TODO_TAG_ID(400, 4015, "Invalid todo-tag id"),
    TODO_TAG_RELATIONSHIP_ALREADY_EXISTS(400, 4016, "todo-tag relationship already exists"),

    USERNAME_DOESNT_EXISTS(401, 4100, "Username doesn't exist"),
    USERNAME_ALREADY_EXISTS(401, 4101, "Username already exist"),
    USERNAME_MUST_BE_SET(401, 4102, "Username cannot be blank"),
    PASSWORD_MUST_BE_SET(401, 4103, "Password cannot be blank"),
    PASSWORD_DONT_MATCH(401, 4104, "Passwords don't match"),
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
