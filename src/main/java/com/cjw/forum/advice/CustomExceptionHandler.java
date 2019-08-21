package com.cjw.forum.advice;

import com.alibaba.fastjson.JSON;
import com.cjw.forum.dto.ResultDto;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import org.omg.CORBA.INTERNAL;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 15:49 2019-08-16
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handlerControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex, Model model) {
        // HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if("application/json".equals(contentType)) {
            ResultDto resultDto;
            if (ex instanceof CustomException) {
                resultDto = ResultDto.errorOf((CustomException) ex);
            } else {
                resultDto = ResultDto.errorOf(CustomErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDto));
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        } else {
            if (ex instanceof CustomException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomErrorCode.SYS_ERROR.getMessage());
            }
        }

        return new ModelAndView("error");
    }

    // private HttpStatus getStatus(HttpServletRequest request) {
    //     Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    //     if(statusCode == null) {
    //         return HttpStatus.INTERNAL_SERVER_ERROR;
    //     }
    //     return HttpStatus.valueOf(statusCode);
    // }
}
