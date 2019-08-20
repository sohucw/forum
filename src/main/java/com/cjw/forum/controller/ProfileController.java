package com.cjw.forum.controller;

import com.cjw.forum.dto.PageDto;
import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.model.User;
import com.cjw.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 20:56 2019-08-15
 */
@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(value = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page, // 当前页面
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        // Cookie[] cookies = request.getCookies();
        // User user = null;
        // if (cookies != null) {
        //     for(Cookie cookie : cookies) {
        //         if(cookie.getName().equals("token")) {
        //             String token = cookie.getValue();
        //             user = userMapper.findByToken(token);
        //             if(user != null) {
        //                 request.getSession().setAttribute("user", user);
        //             }
        //             break;
        //         }
        //     }
        // }
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        if("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else  if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        PageDto list = questionService.list(user.getId(), page, size);
        model.addAttribute("data", list);
        return "profile";
    }
}
