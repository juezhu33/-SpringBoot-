package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    @Insert("insert into user(username,password,create_time,update_time) " +
            "values(#{username},#{md5Pwd},now(),now())")
    void addUser(String username, String md5Pwd);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=now() where id=#{id}")
    void update(User user);

    @Update("update user set user_pic = #{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password= #{md5String},update_time=now() where id= #{id}")
    void updatePwd(String md5String, Integer id);
}
