package com.example.service;

import com.example.pojo.Article;
import com.example.pojo.PageBean;

public interface ArticleService {


    //  添加
    void add(Article article);

    // 分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);
}
