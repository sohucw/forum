package com.cjw.forum.service;

import com.cjw.forum.dto.PageDto;
import com.cjw.forum.dto.QuestionDto;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import com.cjw.forum.mappper.QuestionMapper;
import com.cjw.forum.mappper.UserMapper;
import com.cjw.forum.model.Question;
import com.cjw.forum.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 14:44 2019-08-15
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if(StringUtils.isBlank(questionDto.getTag())) {
            return new ArrayList<>();
        }
        // new StringJoiner("|")
        String[] tags = StringUtils.split(questionDto.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionMapper.listLikeTag(questionDto.getId(), regexpTag);
        List<QuestionDto> collect = questions.stream().map(q -> {
            QuestionDto questionDto1 = new QuestionDto();
             BeanUtils.copyProperties(q, questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());
        return collect;
    }


    public PageDto listAll(String search, Integer page, Integer size) {
        if(StringUtils.isNoneBlank(search)) {
            String[] searchs = StringUtils.split(search, " ");
            search = Arrays.stream(searchs).collect(Collectors.joining("|"));
            // questionMapper.countBySearch(search);
        }
        PageDto pageDto = new PageDto();
        Integer count = questionMapper.countBySearch(search);
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
        List<Question> list = questionMapper.listAllBySearch(search, offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();


        for(Question question: list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto  = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pageDto.setData(questionDtoList);
        return pageDto;
    }

    public PageDto list(Long userId, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer count = questionMapper.countByUserId(userId);
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
        List<Question> list = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();


        for(Question question: list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto  = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pageDto.setData(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Long id) {
        QuestionDto questionDto = new QuestionDto();
        Question question = questionMapper.getById(id);
        if(question == null) {
            throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null) {

            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.create(question);
        } else {

            question.setGmtModified(System.currentTimeMillis());
            Integer num = questionMapper.update(question);
            if(num !=1 ) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        // Question question = questionMapper.getById(id);
        // Integer count = question.getViewCount();

        questionMapper.updateViewCountById(id);
    }
}
