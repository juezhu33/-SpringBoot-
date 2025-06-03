package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select( "select * from user where username=#{username}")
    User findByUserName(String username);

    @Insert( "insert into user(username,password,create_time,update_time) " +
            "values(#{username},#{md5Pwd},now(),now())")
    void addUser(String username, String md5Pwd);
}
