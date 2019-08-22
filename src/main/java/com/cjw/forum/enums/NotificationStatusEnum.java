package com.cjw.forum.enums;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 20:01 2019-08-21
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

}
