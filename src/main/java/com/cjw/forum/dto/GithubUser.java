package com.cjw.forum.dto;

import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 10:13 2019-08-14
 */
@Data
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

}
