package com.cjw.forum.dto;

import com.cjw.forum.model.User;
import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 14:41 2019-08-15
 */
@Data
public class QuestionDto {

    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
