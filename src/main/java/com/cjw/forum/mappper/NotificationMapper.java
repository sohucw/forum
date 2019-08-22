package com.cjw.forum.mappper;

import com.cjw.forum.model.Comment;
import com.cjw.forum.model.Notification;
import com.cjw.forum.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @param <>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 19:59 2019-08-21
 */
@Mapper
@Component
public interface NotificationMapper {
    // @Insert("insert into comment (parent_id,content,type,commentator,gmt_create,gmt_modified) values (#{parentId}, #{content}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified})")
    // void insert(Comment comment);

    @Insert("insert into notification (notifier,receiver,outerid,type,gmt_create,status, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerid}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);
    @Select("select count(1) from notification where receiver = #{userId}")
    Integer countByUserId(Long userId);
    @Select("select * from notification where receiver = #{userId} order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> listByUserId(Long userId, Integer offset, Integer size);
    @Select("select count(*) from notification where status=0 and receiver = #{id}")
    Long unreadCount(@Param("id") Long id);
    @Select("select * from notification where id = #{id}")
    Notification findById(Long id);
    @Update("update notification set status = 1 where id = #{id}")
    void updateRead(Long id);
}
