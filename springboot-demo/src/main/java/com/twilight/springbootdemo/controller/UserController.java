package com.twilight.springbootdemo.controller;

import com.twilight.springbootdemo.entity.User;
import com.twilight.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public User addUser() {
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        return userRepository.save(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
