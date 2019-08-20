package com.cjw.forum.model;

import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:36 2019-08-19
 */
@Data
public class Comment {

    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentator;
}
