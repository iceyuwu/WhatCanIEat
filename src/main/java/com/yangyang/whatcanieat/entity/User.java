package com.yangyang.whatcanieat.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private int uId;
    private String uName;
    private String uImage;
    private String uAccount;
    private String uPassword;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
}
