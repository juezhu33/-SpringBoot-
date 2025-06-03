package com.example.controller;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {


    @GetMapping("/list")
    public Result<String> list() {
        return Result.success("获取文章列表成功,我是所有文章数据");
    }
}
