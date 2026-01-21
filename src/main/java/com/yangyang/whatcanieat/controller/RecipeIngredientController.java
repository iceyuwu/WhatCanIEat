package com.yangyang.whatcanieat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.service.RecipeIngredientService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipeIngredient")
public class RecipeIngredientController {

    @Resource
    private RecipeIngredientService recipeIngredientService;


    /**
     * 添加菜谱-原料关系表
     * @param riList 菜谱-原料关系列表
     * @return
     */
    @PostMapping("/add")
    public Result<List<RecipeIngredient>> add(@RequestBody List<RecipeIngredient> riList ){
        //1.保存信息
         recipeIngredientService.saveBatch(riList);
        //2.返回
        return Result.ok(riList);
    }

    /**
     * 根据菜谱id删除菜谱-原料关系表
     * @param recipeId 菜谱id
     * @return
     */
    @PostMapping("/delete")
    public Result<List<RecipeIngredient>> delete(@RequestParam Integer recipeId){
        //1.根据菜谱id删除整个 菜谱-原料关系表
        QueryWrapper<RecipeIngredient> wrapper = new QueryWrapper<>();
        wrapper
                .eq("rec_id",recipeId);
        recipeIngredientService.remove(wrapper);
        //2.返回结果
        return Result.ok("删除成功！");
    }

    /**
     * 根据菜谱id查找菜谱-原料关系表
     * @param recipeId
     * @return
     */
    @PostMapping("/search")
    public Result<List<RecipeIngredient>> search(@RequestParam Integer recipeId){
        //1.根据菜谱id查找菜谱-原料关系表
        QueryWrapper<RecipeIngredient> wrapper = new QueryWrapper<>();
        wrapper
                .eq("rec_id",recipeId);
        List<RecipeIngredient> list = recipeIngredientService.list(wrapper);
        //2.返回结果
        return Result.ok(list);
    }
}
