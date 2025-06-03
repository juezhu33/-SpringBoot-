package com.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    // 成员变量及注释
    private Integer id;            // 主键ID
    private String username;       // 用户名
    private String password;       // 密码（原代码中变量名拼写错误，应为`password`，修正后）
    private String nickname;       // 昵称
    private String email;// 邮箱
    private String userPic;        // 用户头像地址
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
