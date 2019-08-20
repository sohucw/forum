package com.cjw.forum.dto;

import lombok.Data;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 09:33 2019-08-14
 */
@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
