package com.cjw.forum.service;

import com.cjw.forum.advice.CustomExceptionHandler;
import com.cjw.forum.dto.CommentDto;
import com.cjw.forum.enums.CommentTypeEnum;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import com.cjw.forum.mappper.CommentMapper;
import com.cjw.forum.mappper.QuestionMapper;
import com.cjw.forum.model.Comment;
import com.cjw.forum.model.Question;
import com.cjw.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void insert(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());

        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        if(commentDto.getParentId()== null || commentDto.getParentId() ==0) {
            throw new CustomException(CustomErrorCode.SYS_ERROR);
        }
        if (commentDto.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.getById(commentDto.getParentId());
            if (dbComment == null) {
                throw new CustomException(CustomErrorCode.NOT_COMMENT_EXIST);
            }
            comment.setType(CommentTypeEnum.COMMENT.getType());
            commentMapper.insert(comment);
            // 回复评论
        } else {
            Question dbQuestion = questionMapper.getById(commentDto.getParentId());
            if (dbQuestion == null) {
                throw new CustomException(CustomErrorCode.NOT_QUESTION_EXIST);
            }
            comment.setType(CommentTypeEnum.QUESTION.getType());
            commentMapper.insert(comment);
            questionMapper.updateCommentViewCountById(commentDto.getParentId());
        }


    }
}
