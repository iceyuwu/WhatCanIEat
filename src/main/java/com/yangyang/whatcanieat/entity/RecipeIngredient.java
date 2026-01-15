package com.yangyang.whatcanieat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("recipes_ingredients")
public class RecipeIngredient {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recId;
    private Long ingId;
    private Integer number;
    private Integer unit;
    private Integer type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
