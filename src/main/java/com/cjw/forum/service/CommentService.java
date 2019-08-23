package com.cjw.forum.service;

import com.cjw.forum.dto.CommentCreateDto;
import com.cjw.forum.dto.CommentDto;
import com.cjw.forum.enums.CommentTypeEnum;
import com.cjw.forum.enums.NotificationEnum;
import com.cjw.forum.enums.NotificationStatusEnum;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import com.cjw.forum.mappper.CommentMapper;
import com.cjw.forum.mappper.NotificationMapper;
import com.cjw.forum.mappper.QuestionMapper;
import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.model.Comment;
import com.cjw.forum.model.Notification;
import com.cjw.forum.model.Question;
import com.cjw.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:50 2019-08-19
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(CommentCreateDto commentCreateDto, User user) {
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());

        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        if(commentCreateDto.getParentId()== null || commentCreateDto.getParentId() ==0) {
            throw new CustomException(CustomErrorCode.SYS_ERROR);
        }
        // 回复评论
        if (commentCreateDto.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.getById(commentCreateDto.getParentId());
            if (dbComment == null) {
                throw new CustomException(CustomErrorCode.NOT_COMMENT_EXIST);
            }
            // 回复问题
            Question dbQuestion = questionMapper.getById(dbComment.getParentId());
            if (dbQuestion == null) {
                throw new CustomException(CustomErrorCode.NOT_QUESTION_EXIST);
            }
            comment.setType(CommentTypeEnum.COMMENT.getType());
            commentMapper.insert(comment);
            // 增加评论数
            commentMapper.updateCommentViewCountById(commentCreateDto.getParentId());
            // 创建通知

            createNotify(comment, dbComment.getCommentator(), user.getName(), dbQuestion.getTitle(), NotificationEnum.REPLY_COMMENT, dbQuestion.getId());
        } else {
            // 回复问题
            Question dbQuestion = questionMapper.getById(commentCreateDto.getParentId());
            if (dbQuestion == null) {
                throw new CustomException(CustomErrorCode.NOT_QUESTION_EXIST);
            }
            comment.setType(CommentTypeEnum.QUESTION.getType());
            commentMapper.insert(comment);
            questionMapper.updateCommentViewCountById(commentCreateDto.getParentId());
            // 创建通知
            createNotify(comment, dbQuestion.getCreator(),user.getName(), dbQuestion.getTitle(), NotificationEnum.REPLY_QUESTION, dbQuestion.getId());

        }

    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outTitle, NotificationEnum notificationEnum, Long outerid) {
        if(receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        // Long parentId = comment.getParentId();
        notification.setOuterid(outerid);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDto> listByTargetId(Long id, Integer type) {
        List<Comment> comments = commentMapper.listByQuestionId(type, id);
        if(comments.size() ==0 ) {
            return  new ArrayList<>();
        }
        // 使用lamda 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        // List<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toList());
        List<Long> userIds = new ArrayList<Long>();
        userIds.addAll(commentators);
        List<User> users = userMapper.findUserInIds(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment - commentDto
        List<CommentDto> collects = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return  collects;
    }
}
