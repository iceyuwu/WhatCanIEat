package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String image;
    private String account;
    private String password;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
