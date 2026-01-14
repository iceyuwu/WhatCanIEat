package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangyang.whatcanieat.entity.Ingredient;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.service.IngredientService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Resource
    private IngredientService ingredientService;

    /**
     * 原料添加接口
     * @param ingredient 原料信息
     * @return 更新后的原料信息
     */
    @PostMapping("/add")
    public Result<Ingredient> add(@RequestBody Ingredient ingredient){
        //1.获取原料信息
        //2.判断是否已存在原料
        QueryWrapper<Ingredient> wrapper = new QueryWrapper<>();
        wrapper
                .eq("name",ingredient.getName());
        Ingredient ingredientInDB = ingredientService.getOne(wrapper);
        if(ingredientInDB != null) {
            return Result.fail("原料已存在！");
        }
        //3.保存原料信息
        boolean save = ingredientService.save(ingredient);
        //4.返回原料信息
        return Result.ok(ingredient,"原料保存成功！");
    }

    /**
     * 原料修改接口
     * @param ingredient 原料信息
     * @return 更新后的原料信息
     */
    @PostMapping("/update")
    public Result<Ingredient> update(@RequestBody Ingredient ingredient){
        //1.根据id找到需要更改的原料
        Ingredient ingredientInDB = ingredientService.getById(ingredient.getId());
        if(ingredientInDB == null){
            return Result.fail("没有此原料！");
        }
        //2.更改原料信息
        ingredientService.updateById(ingredient);
        //3.返回
        return Result.ok(ingredient , "更新成功！");
    }

    /**
     * 删除原料接口
     * @param ingredient 原料信息
     * @return 更新后原料信息
     */
    @PostMapping("/delete")
    public Result<Ingredient> delete(@RequestBody Ingredient ingredient){
        //1.根据id删除
        ingredientService.removeById(ingredient);
        //2.返回删除结果
        return Result.ok(ingredient , "删除成功！");
    }

    /**
     * 根据id查找原料接口
     * @param id 原料id
     * @return 原料信息
     */
    @PostMapping("/search/id")
    public Result<Ingredient> searchById(@RequestParam int id){
        //1.根据id查找原料
        Ingredient ingredient = ingredientService.getById(id);

        //2.返回结果
        return Result.ok(ingredient);
    }

    /**
     * 根据名字查找原料
     * @param name 原料名
     * @return 查找到的原料
     */
    @PostMapping("/search/name")
    public Result<Ingredient> searchByName(@RequestParam String name){
        //1.根据名字查找原料
        QueryWrapper<Ingredient> wrapper = new QueryWrapper<>();
        wrapper
                .eq("name",name);
        Ingredient ingredient = ingredientService.getOne(wrapper);
        //2.返回结果
        return Result.ok(ingredient);
    }

    @PostMapping("/search/ids")
    public Result<List<Ingredient>> searchByIds(@RequestBody List<Long> ids){
        //1.传入多个id
        //2.根据多个id查找
        List<Ingredient> ingredientList = ingredientService.listByIds(ids);
        //3.返回结果
        return Result.ok(ingredientList);
    }
}
