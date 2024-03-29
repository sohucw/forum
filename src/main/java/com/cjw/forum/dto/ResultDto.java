package com.cjw.forum.dto;

import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import lombok.Data;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 17:29 2019-08-19
 */
@Data
public class ResultDto<T> {

    private Integer code;
    private String message;
    private T data;

    public static ResultDto errorOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomErrorCode errorCode) {
        return  errorOf(errorCode.getCode(), errorCode.getMessage());
    }
    public static ResultDto okOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }
    public static <T> ResultDto okOf(T t) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }


    public static ResultDto errorOf(CustomException e){
        return errorOf(e.getCode(), e.getMessage());
    }
}
