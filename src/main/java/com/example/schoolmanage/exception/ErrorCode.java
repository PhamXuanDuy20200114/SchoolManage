package com.example.schoolmanage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;



@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_OBJECT(400, "Not Found Object", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(1001, "Token has already expired!", HttpStatus.UNAUTHORIZED),
    WRONG_INPUT(1002, "Email|password is wrong!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(3, "Username must be at least 3 characters!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(4, "Password must be at least 8 characters!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(5, "User not existed!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(6, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(7, "You dont have permission!", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
            this.message = message;
            this.code = code;
            this.httpStatusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    public void setCode(int code) {
        this.code = code;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



