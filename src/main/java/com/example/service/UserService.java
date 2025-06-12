package com.example.service;

import com.example.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);

    //修改
    void update(User user);

    //修改头像
    void updateAvatar(String avatarUrl);

    //修改密码
    void updatePwd(String newPwd);
}
