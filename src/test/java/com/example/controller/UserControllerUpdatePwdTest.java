package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.Md5Util;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerUpdatePwdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setPassword(Md5Util.getMD5String("oldpassword")); // 原密码
        testUser.setCreateTime(LocalDateTime.now());
        testUser.setUpdateTime(LocalDateTime.now());

        // 模拟ThreadLocal中的用户信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "testuser");
        ThreadLocalUtil.set(claims);
    }

    @Test
    void testUpdatePwdSuccess() throws Exception {
        // 模拟用户存在
        when(userService.findByUserName("testuser")).thenReturn(testUser);

        Map<String, String> params = new HashMap<>();
        params.put("old_pwd", "oldpassword");
        params.put("new_pwd", "newpassword");
        params.put("re_pwd", "newpassword");

        mockMvc.perform(patch("/user/updatePwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("操作成功"));

        verify(userService, times(1)).updatePwd("newpassword");
    }

    @Test
    void testUpdatePwdWrongOldPassword() throws Exception {
        // 模拟用户存在
        when(userService.findByUserName("testuser")).thenReturn(testUser);

        Map<String, String> params = new HashMap<>();
        params.put("old_pwd", "wrongoldpassword"); // 错误的原密码
        params.put("new_pwd", "newpassword");
        params.put("re_pwd", "newpassword");

        mockMvc.perform(patch("/user/updatePwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("原密码填写不正确"));

        verify(userService, never()).updatePwd(anyString());
    }

    @Test
    void testUpdatePwdPasswordMismatch() throws Exception {
        // 模拟用户存在
        when(userService.findByUserName("testuser")).thenReturn(testUser);

        Map<String, String> params = new HashMap<>();
        params.put("old_pwd", "oldpassword");
        params.put("new_pwd", "newpassword");
        params.put("re_pwd", "differentpassword"); // 两次密码不一致

        mockMvc.perform(patch("/user/updatePwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("两次密码输入不一致"));

        verify(userService, never()).updatePwd(anyString());
    }

    @Test
    void testUpdatePwdMissingParameters() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("old_pwd", "oldpassword");
        // 缺少 new_pwd 和 re_pwd

        mockMvc.perform(patch("/user/updatePwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("缺少必要参数"));

        verify(userService, never()).updatePwd(anyString());
    }
}
