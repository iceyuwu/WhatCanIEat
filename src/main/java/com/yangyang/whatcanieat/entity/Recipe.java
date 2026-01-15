package com.yangyang.whatcanieat.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("recipes")
public class Recipe {
    private long id;
    private String name;
    private String text;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private long UId;

}
