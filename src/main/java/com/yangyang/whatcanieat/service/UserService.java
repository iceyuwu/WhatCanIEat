package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User getUserById(int id){
        User user1 = userMapper.selectById(id);
        return user1;
    }

}
