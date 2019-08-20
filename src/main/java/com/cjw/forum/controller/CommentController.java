package com.cjw.forum.controller;

import com.cjw.forum.dto.CommentDto;
import com.cjw.forum.dto.ResultDto;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.mappper.CommentMapper;
import com.cjw.forum.model.Comment;
import com.cjw.forum.model.User;
import com.cjw.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomErrorCode.NO_LOGIN);
        }
        commentService.insert(commentDto, user);
        return  ResultDto.okOf();
    }
}
