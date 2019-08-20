package com.cjw.forum.enums;

import org.omg.CORBA.INTERNAL;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 17:33 2019-08-19
 */
public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2);
    private Integer type;
    public Integer getType(){
        return type;
    }
    CommentTypeEnum(Integer type) {
        this.type = type;
    }

}
