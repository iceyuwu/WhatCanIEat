package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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


    /**
     * 登录接口
     * @param account 账号
     * @param password 密码
     * @return 用户信息
     */
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

    /**
     * 注册接口
     * @param account 账号
     * @param password 密码
     * @param name 用户名
     * @return 用户信息
     */
    @PostMapping("/register")
    public Result<User> register(@RequestParam String account , @RequestParam String password , @RequestParam String name){
        //1.接受新账号新密码
        //2.检查新账号是否已存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("account",account);
        User userInDB = userService.getOne(userQueryWrapper);
        if(userInDB != null){
            return Result.fail("账号已存在！请尝试登录。");
        }
        //3.在数据库中保存
        User userSave = new User();
        userSave.setAccount(account);
        userSave.setPassword(password);
        userSave.setName(name);
        userSave.setCreateTime(LocalDateTime.now());
        userSave.setUpdateTime(LocalDateTime.now());
        boolean save = userService.save(userSave);
        //4.再次查询用户信息
        QueryWrapper<User> userQueryWrapperNew = new QueryWrapper<>();
        userQueryWrapperNew
                .eq("account",account);
        userSave = userService.getOne(userQueryWrapper);
        return Result.ok(userSave);
    }

    /**
     * 展示用户界面接口
     * @param id 用户id
     * @return 用户信息
     */
    @PostMapping("/userinfo")
    public Result<User> userInformation(@RequestParam int id){
        //1.根据用户id查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("id",id);
        User userInDB = userService.getOne(userQueryWrapper);
        if(userInDB != null){
            return Result.fail("未查找到用户。");
        }
        //2.返回用户信息
        return Result.ok(userInDB);
    }

    /**
     *删除接口
     * @param account 账号
     * @param password 密码
     * @return 成功信息
     */
    @PostMapping("/deleteuser")
    public Result<User> deleteuser(@RequestParam String account , @RequestParam String password){
        //1.根据账密判断是否是用户本人操作
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("account",account)
                .eq("password",password);
        User user = userService.getOne(userQueryWrapper);
        if (user == null){
            return Result.fail("删除失败！账户或密码出错。");
        }
        //2.删除账户
        userService.removeById(user.getId());
        return Result.ok(user, "删除成功！");
    }

    /**
     * 更新用户信息接口
     * @param user 用户
     * @return 更新后的用户信息
     */
    @PostMapping("/update")
    public Result<User> updateuser(@RequestBody User user){
        //1.传入用户对象
        //2.修改用户信息
        userService.updateById(user);
        //3.查找出更新后的用户数据，返回
        user = userService.getById(user.getId());
        return Result.ok(user);
    }

    //TODO 用户忘记密码找回接口


}
