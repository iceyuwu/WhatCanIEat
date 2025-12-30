package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("users")
public class User {
    @TableId(value = "u_id", type = IdType.AUTO)
    private int uId;
    private String uName;
    private String uImage;
    private String uAccount;
    private String uPassword;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
}
