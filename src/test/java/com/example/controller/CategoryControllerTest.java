package com.example.controller;

import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.utils.ThreadLocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setCategoryName("测试分类");
        testCategory.setCategoryAlias("test");
        testCategory.setCreateUser(1);
        testCategory.setCreateTime(LocalDateTime.now());
        testCategory.setUpdateTime(LocalDateTime.now());

        // 模拟ThreadLocal中的用户信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "testuser");
        ThreadLocalUtil.set(claims);
    }

    @Test
    void testDeleteCategorySuccess() throws Exception {
        // 不需要mock，因为delete方法返回void
        doNothing().when(categoryService).deleteById(1);

        mockMvc.perform(delete("/category")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("操作成功"));

        verify(categoryService, times(1)).deleteById(1);
    }

    @Test
    void testAddCategorySuccess() throws Exception {
        doNothing().when(categoryService).add(any(Category.class));

        Category newCategory = new Category();
        newCategory.setCategoryName("新分类");
        newCategory.setCategoryAlias("new");

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("操作成功"));

        verify(categoryService, times(1)).add(any(Category.class));
    }
}
