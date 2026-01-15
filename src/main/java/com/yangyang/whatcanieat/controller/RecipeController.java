package com.yangyang.whatcanieat.controller;


import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.vo.RecipeVO;
import com.yangyang.whatcanieat.service.RecipeIngredientService;
import com.yangyang.whatcanieat.service.RecipeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Resource
    private RecipeService recipeService;
    @Resource
    private RecipeIngredientService recipeIngredientService;

    /**
     * 添加菜谱接口
     * @param recipeVO
     * @return
     */
    @PostMapping("/add")
    public Result<RecipeVO> add(@RequestBody RecipeVO recipeVO){
        //1.获取菜谱信息（可重复）
        //2.1保存菜谱信息
        Recipe recipe = new Recipe();
        recipe.setName(recipeVO.getName());
        recipe.setText(recipeVO.getText());
        recipe.setCreateTime(LocalDateTime.now());
        recipe.setUpdateTime(LocalDateTime.now());
        recipe.setUId(recipeVO.getUId());
        recipeService.save(recipe);
        //2.2保存菜谱-原料关系表
        //从菜谱VO中获取菜谱-原料关系列表
        List<RecipeIngredient> recipeIngredientList = recipeVO.getRecipeIngredientList();
        //遍历设置菜谱-原料中缺少的信息
        for (RecipeIngredient recipeIngredient : recipeIngredientList) {
            recipeIngredient.setRecId(recipe.getId());
            recipeIngredient.setCreateTime(LocalDateTime.now());
            recipeIngredient.setUpdateTime(LocalDateTime.now());
        }
        //保存进数据库
        recipeIngredientService.saveBatch(recipeIngredientList);
        //3.返回结果
        recipeVO.setId(recipe.getId());
        recipeVO.setCreateTime(recipe.getCreateTime());
        recipeVO.setUpdateTime(recipe.getUpdateTime());
        recipeVO.setRecipeIngredientList(recipeIngredientList);
        return Result.ok(recipeVO);
    }
}
