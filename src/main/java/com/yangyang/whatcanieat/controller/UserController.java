package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @GetMapping("/id/{id}")
    public User getById(@PathVariable int id){
        User user1 = userService.getById(id);
        return user1;
    }

    @GetMapping("/page/{pageNum}/size/{pageSize}")
    public Page<User> getPage(@PathVariable int pageNum, @PathVariable int pageSize){
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> userPage = userService.page(page);
        return userPage;
    }
}
