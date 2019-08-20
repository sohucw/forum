package com.cjw.forum.exception;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:01 2019-08-16
 */

public class CustomException extends  RuntimeException {

    private String message;
    private Integer code;

    public CustomException(ICustomErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
