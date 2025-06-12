package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated //开启校验
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户存在吗
        User user = userService.findByUserName(username);
        if (user == null) {
            //没有占用
            userService.register(username, password);
            return Result.success();
        } else {
            //被占用
            return Result.error("用户名被占用");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }
        //密码校验
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {

            // 创建一个Map对象用于存储JWT的负载信息
            Map<String, Object> claims = new HashMap<>();
            // 向负载中添加用户名信息，以便在JWT中携带用户身份
            claims.put("username", username);
            // 向负载中添加用户ID信息，进一步增强用户身份的唯一性和可追溯性
            claims.put("id", user.getId());
            // 调用JwtUtil的genToken方法生成JWT，将用户信息编码进令牌
            String token = JwtUtil.genToken(claims);
            // 返回生成的JWT，此处通过Result的success方法封装返回结果，表示操作成功
            return Result.success(token);

        }
        return Result.error("密码错误");

    }

    //  获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader("Authorization") String token*/) {

//        Map<String, Object> map = JwtUtil.parseToken(token);
//        //根据用户名查询用户
//        String username = (String) map.get("username");
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    //  更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }


    //  更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //  更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }
        // 获取当前登录用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        //  判断原密码是否正确
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }
        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码输入不一致");
        }
        // 更新密码
        userService.updatePwd(newPwd);

        return Result.success();
    }

}
