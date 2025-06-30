package com.example.userapi.service.impl;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.entity.User;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.mapper.UserMapper;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

//    @Autowired
//    private UserMapper userMapper;

    @Override
    public User createUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return repo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

//    @Override
//    public int updateUser(User user) {
//        return userMapper.updateUser(user);
//    }
}