package com.example.service.impl;

import com.example.mapper.CategoryMapper;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
       return categoryMapper.list(id);

    }

    @Override
    public Category detail(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        // 获取当前登录用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // 先查询分类是否存在且属于当前用户
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        if (!category.getCreateUser().equals(userId)) {
            throw new RuntimeException("只能删除自己创建的分类");
        }

        categoryMapper.deleteById(id);
    }


}
