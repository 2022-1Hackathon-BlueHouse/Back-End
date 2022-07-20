package com.study.boardstudy.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomError {
    public MemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, "member is not found");
    }
}
