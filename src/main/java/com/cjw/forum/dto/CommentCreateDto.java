package com.cjw.forum.dto;

import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:43 2019-08-19
 */
@Data
public class CommentCreateDto {

    private Long parentId;
    private String content;
    private Integer type;
}
