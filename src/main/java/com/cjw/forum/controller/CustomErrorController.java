package com.cjw.forum.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 11:26 2019-08-19
 */
@Controller("/error")
public class CustomErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus status = getStatus(request);
        if(status.is4xxClientError()) {
            model.addAttribute("message", "你请求地址不对 要不然换个姿势");


        }
        if(status.is5xxServerError()) {

            model.addAttribute("message", "服务器冒烟了！");

        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
