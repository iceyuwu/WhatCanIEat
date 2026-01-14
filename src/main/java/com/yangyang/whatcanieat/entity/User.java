package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("users")
public class User {
    private long id;
    private String name;
    private String image;
    private String account;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
