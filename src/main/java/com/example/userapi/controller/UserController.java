package com.example.userapi.controller;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.entity.User;
import com.example.userapi.response.ApiResponse;
import com.example.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> create(@RequestBody UserDTO dto) {
        return ApiResponse.success(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<User> get(@PathVariable Long id) {
        return ApiResponse.success(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }

//    @PutMapping("/{id}")
//    public ApiResponse<Integer> update(@PathVariable Long id, @RequestBody UserDTO dto) {
//        User user = userService.getUser(id);
//        user.setName(dto.getName());
//        user.setEmail(dto.getEmail());
//        return ApiResponse.success(userService.updateUser(user));
//    }
}