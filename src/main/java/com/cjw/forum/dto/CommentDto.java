package com.cjw.forum.dto;

import com.cjw.forum.model.User;
import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 15:47 2019-08-20
 */
@Data
public class CommentDto {

    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentator;
    private User user;
}
