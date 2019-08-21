package com.cjw.forum.controller;

import com.cjw.forum.dto.CommentCreateDto;
import com.cjw.forum.dto.ResultDto;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.model.User;
import com.cjw.forum.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:38 2019-08-19
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            return ResultDto.errorOf(CustomErrorCode.NOT_NULL);
        }
        commentService.insert(commentCreateDto, user);
        return  ResultDto.okOf();
    }
}
