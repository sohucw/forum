package com.cjw.forum.mappper;

import com.cjw.forum.dto.CommentDto;
import com.cjw.forum.model.Comment;
import com.cjw.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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
    @Select("select * from comment where id = #{parentId})")
    Comment getById(Long parentId);
}
