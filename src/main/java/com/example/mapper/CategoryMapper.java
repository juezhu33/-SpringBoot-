package com.example.mapper;


import com.example.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {

//    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time)" +
//            "values (#{categoryName}, #{categoryAlias}, #{createUser}, now(), now())")
//    void add(Category category);
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time)" +
            "values (#{category.categoryName}, #{category.categoryAlias}, #{userId}, now(), now())")
    void add(@Param("category") Category category, @Param("userId") Integer id);
}



