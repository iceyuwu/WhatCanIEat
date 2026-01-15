package com.yangyang.whatcanieat.entity.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.yangyang.whatcanieat.entity.Ingredient;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeVO {
    private long id;
    private String name;
    private String text;
    private long UId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //菜谱-原料关系列表 一个菜谱可以有多个原料
    private List<RecipeIngredient> recipeIngredientList;

}
