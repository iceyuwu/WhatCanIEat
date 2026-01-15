package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("recipes_ingredients")
public class RecipeIngredient {
    private long id;
    private long recId;
    private long ingId;
    private int number;
    private int unit;
    private int type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
