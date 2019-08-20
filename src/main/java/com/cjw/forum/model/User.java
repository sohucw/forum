package com.cjw.forum.model;

import lombok.Data;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:42 2019-08-14
 */
@Data
public class User {

    private Long id;

    private String name;

    private String accountId;

    private String token;

    private String avatarUrl;

    private Long gmtCreate;

    private Long gmtModified;


}
