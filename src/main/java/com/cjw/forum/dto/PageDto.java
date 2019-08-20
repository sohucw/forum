package com.cjw.forum.dto;

import jdk.nashorn.internal.ir.IdentNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 16:26 2019-08-15
 */
@Data
public class PageDto {
    private boolean showPrevious; // 是否有向前按钮
    private boolean showFirstPage; // 第一页按钮
    private boolean showNext; // 是否有向后按钮
    private boolean showEndPage; // 最后按钮
    private Integer page; // 第几页 currPage
    private Integer size; // 每页多少条
    private List<QuestionDto> questions;

    private Integer totalPage;

    private List<Integer> pages = new ArrayList<>(); // 页码数

    private Integer totalSize;


    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage = totalPage;
        pages.add(page);
        for (int i =1; i<=3; i++) {
            if(page -i >0) {
                pages.add(0, page -i); // 不明白
            }
            if(page+i <= totalPage) {
                pages.add(page+i);
            }
        }
        if(page < 1) {
            page = 1;
        }
        if(page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        // 无论那一页 都是显示7个数
        // 1 2 3 4 5 6 7
        // 123
        // 2




        // 是否展示上一页
        if(page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        // 是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        // 是否展示第一页
        if(pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        // 是否展示最后一页
        if(pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
