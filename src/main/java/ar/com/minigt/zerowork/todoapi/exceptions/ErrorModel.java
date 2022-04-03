package ar.com.minigt.zerowork.todoapi.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorModel {

    private Integer code;
    @JsonProperty(value = "extended_code")
    private Integer extendedCode;
    private String reason;

    public ErrorModel(TodoException ex) {
        this.code = ex.getStatusCode();
        this.extendedCode = ex.getExtendedCode();
        this.reason = ex.getReason();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getExtendedCode() {
        return extendedCode;
    }

    public void setExtendedCode(Integer extendedCode) {
        this.extendedCode = extendedCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}