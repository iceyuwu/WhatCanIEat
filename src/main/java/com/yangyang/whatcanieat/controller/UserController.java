package com.yangyang.whatcanieat.controller;

import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        User user1 = userService.getUserById(id);
        return user1;
    }
}
