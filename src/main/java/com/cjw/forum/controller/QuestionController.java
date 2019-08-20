package com.cjw.forum.controller;

import com.cjw.forum.dto.QuestionDto;
import com.cjw.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 10:06 2019-08-16
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDto questionDto = questionService.getById(id);
        // 累计阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDto);
        return "question";
    }
}
