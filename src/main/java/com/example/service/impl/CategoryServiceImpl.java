package com.example.service.impl;

import com.example.mapper.CategoryMapper;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

//    @Override
//    public void add(Category category) {
//        Map<String, Object> map = ThreadLocalUtil.get();
//        Integer id = (Integer) map.get("id");
//        category.setCreateUser(id); // 将用户ID设置到category对象中
//        categoryMapper.add(category);
//    }
    @Override
    public void add(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        categoryMapper.add(category, id); // 保持原有调用方式
    }


}
