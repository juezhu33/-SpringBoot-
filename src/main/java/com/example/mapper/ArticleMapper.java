package com.example.mapper;

import com.example.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {


    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time)" +
            "values(#{article.title}, #{article.content}, #{article.coverImg}, #{article.state}, #{article.categoryId}, #{article.createUser}, #{article.createTime}, #{article.updateTime})")
    void add(@Param("article")Article article);



    List<Article> list(Integer userId, String categoryId, String state);
}
