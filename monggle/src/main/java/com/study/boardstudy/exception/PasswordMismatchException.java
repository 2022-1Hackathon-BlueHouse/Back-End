package com.study.boardstudy.exception;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends CustomError {
    public PasswordMismatchException() {
        super(HttpStatus.UNAUTHORIZED, "password mismatch");
    }
}
