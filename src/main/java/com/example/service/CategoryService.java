package com.example.service;

import com.example.pojo.Category;

import java.util.List;

public interface CategoryService {
    //添加分类
    void add(Category category);

    //查询所有分类
    List<Category> list();

    //查询分类详情
    Category detail(Integer id);

    //修改分类
    void update(Category category);

    //删除分类
    void deleteById(Integer id);
}
