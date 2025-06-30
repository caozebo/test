package com.example.userapi.service;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.entity.User;

public interface UserService {
    User createUser(UserDTO dto);
    User getUser(Long id);
    void deleteUser(Long id);
//    int updateUser(User user);
}