package com.cjw.forum.mappper;

import com.cjw.forum.dto.QuestionDto;
import com.cjw.forum.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 22:29 2019-08-14
 */
@Mapper
@Component
public interface QuestionMapper {
    @Insert("insert into question (title,description,tag,gmt_create,gmt_modified,creator) values (#{title}, #{description}, #{tag}, #{gmtCreate}, #{gmtModified}, #{creator})")
    void create(Question question);

    // @Select("select * from question order by gmt_create desc limit #{offset}, #{size}")
    // List<Question> listAll(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> listLikeTag(Long id, String tag);

    // @Select("select count(1) from question")
    // Integer count();

    @Select({"<script>",
            "select * from question",
            // "WHERE 1=1",
            "<when test='search!=null and search!=\"\"'>",
            "where title regexp #{search}",
            "</when>",
            "order by gmt_create desc limit #{offset}, #{size}",
            "</script>"})
    // @Select("select * from question where title regexp #{search} order by gmt_create desc limit #{offset}, #{size}")
    List<Question> listAllBySearch(@Param("search") String search, @Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select({"<script>",
            "select count(*) from question",
            // "WHERE 1=1",
            "<when test='search!=null and search!=\"\"'>",
            "where title regexp #{search}",
            "</when>",
            "</script>"})
    // @Select("select count(1) from question where title regexp ${search}")
    Integer countBySearch(@Param("search") String search);

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param("userId") Long userId, Integer offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);
    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    Integer update(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void updateViewCountById(Long id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    void updateCommentViewCountById(Long id);



}
