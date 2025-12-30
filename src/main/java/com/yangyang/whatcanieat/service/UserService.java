package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
