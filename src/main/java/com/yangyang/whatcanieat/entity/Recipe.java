package com.yangyang.whatcanieat.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("recipes")
public class Recipe {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String text;
    private Long UId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
