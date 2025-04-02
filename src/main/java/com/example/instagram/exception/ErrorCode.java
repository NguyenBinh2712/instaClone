package com.example.instagram.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION"),
    USER_EXISTED(1001,"USER_EXISTED"),
    USER_NOT_FOUND(1002,"USER NOT FOUND"),
    UNAUTHENTICATED(1003," UNAUTHENTICATED")
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
