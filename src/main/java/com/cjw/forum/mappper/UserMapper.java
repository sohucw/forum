package com.cjw.forum.mappper;

import com.cjw.forum.model.Question;
import com.cjw.forum.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:40 2019-08-14
 */
@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{creator}")
    User findById(Long creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    Integer update(User user);

    // @Select("select * from user where id in (#{commentators})")
    @Select({
            "<script>",
            "select",
            "*",
            "from user",
            "where id in",
            "<foreach collection='commentators' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    List<User> findUserInIds(@Param("commentators") List<Long> commentators);
}
