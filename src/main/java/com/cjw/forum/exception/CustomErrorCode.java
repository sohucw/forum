package com.cjw.forum.exception;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:12 2019-08-16
 */
public enum  CustomErrorCode implements ICustomErrorCode {
    QUESTION_NOT_FOUND(2001,"这个问题不存在了"),
    TARGET_PARENT_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录"),
    SYS_ERROR(2004,"系统异常"),
    NOT_COMMENT_EXIST(2006,"回复的评论不存在"),
    NOT_QUESTION_EXIST(2007,"回复的问题不存在"),
    NOT_NULL(2008,"输入内容不能为空"),
    READ_NOTFICATION_FAIL(2009,"读的别人的信息"),
    NOT_FOUND_NOTFICATION(2010,"没找到改通知");
    private String message;
    private Integer code;

    CustomErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
