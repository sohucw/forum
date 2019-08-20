package com.cjw.forum.service;

import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 11:04 2019-08-16
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        User dbUser= userMapper.findByAccountId(user.getAccountId());

        if (dbUser == null) {
            user.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        } else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
