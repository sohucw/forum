package com.cjw.forum.service;

import com.cjw.forum.dto.NotificationDto;
import com.cjw.forum.dto.PageDto;
import com.cjw.forum.enums.NotificationEnum;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import com.cjw.forum.mappper.NotificationMapper;
import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.model.Notification;
import com.cjw.forum.model.Question;
import com.cjw.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 20:45 2019-08-21
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    public PageDto list(Long userId, Integer page, Integer size) {
        PageDto<NotificationDto> pageDto = new PageDto<NotificationDto>();
        Integer count = notificationMapper.countByUserId(userId);
        Integer totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size +1;
        }

        // 5*(i-1)
        Integer offset = size * (page-1);

        if(page < 1) {
            page = 1;
        }
        if(page > totalPage) {
            page = totalPage;
        }
        pageDto.setPagination(totalPage, page);
        List<Notification> notifications = notificationMapper.listByUserId(userId, offset, size);
        if(notifications.size() == 0) {
            return  pageDto;
        }
        // Set<Long> disUserIds = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
        // ArrayList<Long> userIds = new ArrayList<>(disUserIds);
        //
        // List<User> users = userMapper.findUserInIds(userIds);
        // Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), u -> u));
        //



        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notifications.forEach(notification -> {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDtoList.add(notificationDto);
        });
        pageDto.setData(notificationDtoList);
        return  pageDto;
    }

    public Long unreadCount(Long id) {
        return notificationMapper.unreadCount(id);
    }

    public NotificationDto read(Long id, User user) {

        Notification notification = notificationMapper.findById(id);
        if(notification == null) {
            throw new CustomException(CustomErrorCode.NOT_FOUND_NOTFICATION);
        }
        if (notification.getReceiver() != user.getId()) {
            throw new CustomException(CustomErrorCode.READ_NOTFICATION_FAIL);
        }
        // 更新已读
        notificationMapper.updateRead(id);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return  notificationDto;
    }
}
