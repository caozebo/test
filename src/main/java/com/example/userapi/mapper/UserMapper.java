package com.example.userapi.mapper;

import com.example.userapi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insertUser(User user);

    User selectUserById(@Param("id") Long id);

    List<User> selectAllUsers();

    int updateUser(User user);

    int deleteUserById(@Param("id") Long id);
}
