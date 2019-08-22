package com.cjw.forum.controller;

import com.cjw.forum.dto.NotificationDto;
import com.cjw.forum.dto.PageDto;
import com.cjw.forum.enums.NotificationEnum;
import com.cjw.forum.model.Notification;
import com.cjw.forum.model.User;
import com.cjw.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 22:05 2019-08-21
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String index(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        NotificationDto notificationDto = notificationService.read(id, user);
        if(NotificationEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDto.getType()) {
            return "redirect:/question/"  +  notificationDto.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
