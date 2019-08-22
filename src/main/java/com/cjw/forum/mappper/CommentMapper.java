package com.cjw.forum.mappper;

import com.cjw.forum.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:45 2019-08-19
 */
@Mapper
@Component
public interface CommentMapper {
    @Insert("insert into comment (parent_id,content,type,commentator,gmt_create,gmt_modified) values (#{parentId}, #{content}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified})")
    void insert(Comment comment);
    @Select("select * from comment where id = #{parentId}")
    Comment getById(Long parentId);

    @Select("select * from comment where type = #{type} and parent_id = #{id} order by gmt_create desc")
    List<Comment> listByQuestionId(@Param("type") Integer type, @Param("id") Long id);

    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void updateCommentViewCountById(Long id);
}
