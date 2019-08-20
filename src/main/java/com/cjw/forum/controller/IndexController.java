package com.cjw.forum.controller;

import com.cjw.forum.dto.PageDto;
import com.cjw.forum.mappper.QuestionMapper;
import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 18:53 2019-08-13
 */
@Controller
public class IndexController {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page, // 当前页面
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PageDto pageDto = questionService.list(page, size);
        model.addAttribute("data", pageDto);
        return "index";
    }

}
