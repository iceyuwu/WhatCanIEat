package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public Result<User> login(@RequestParam String account , @RequestParam String password){
        //1.根据账户查询数据库
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("account",account);
        User user = userService.getOne(userQueryWrapper);
        //2.比对密码是否正确
        if (user == null){
            return Result.fail("没有这个账号");
        }
        String userPasswordInDB = user.getPassword();
        if (userPasswordInDB.equals(password)){
            user.setPassword("");
            return Result.ok(user);
        }else{
            return Result.fail("登录失败");
        }
    }
}
