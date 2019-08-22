package com.cjw.forum.dto;

import lombok.Data;

import java.util.List;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 18:50 2019-08-21
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}
