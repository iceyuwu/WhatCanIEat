package com.yangyang.whatcanieat.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.entity.vo.RecipeVO;
import com.yangyang.whatcanieat.service.RecipeIngredientService;
import com.yangyang.whatcanieat.service.RecipeService;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Resource
    private RecipeService recipeService;
    @Resource
    private RecipeIngredientService recipeIngredientService;
    @Resource
    private UserService userService;

    /**
     * 添加菜谱接口
     * @param recipeVO
     * @return
     */
//    @PostMapping("/add")
    public Result<RecipeVO> addComplex(@RequestBody RecipeVO recipeVO){
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

    /**
     * 添加菜谱接口
     * @param recipe
     * @return
     */
    @PostMapping("/add")
    public Result<Recipe> add(@RequestBody Recipe recipe){
        //0.获取用户信息
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户不存在！");
        }
        //1.保存菜谱
        recipe.setUId(user.getId());
        recipe.setCreateTime(LocalDateTime.now());
        recipe.setUpdateTime(LocalDateTime.now());
        recipeService.save(recipe);
        //2.返回菜谱信息
        return Result.ok(recipe,"菜谱保存成功！");
    }

    /**
     * 菜谱修改接口
     * @param recipe 菜谱信息
     * @return
     */
    @PostMapping("/update")
    public Result<Recipe> update(@RequestBody Recipe recipe){
        //1.判断是否有这个菜谱
        Recipe recipeInDB = recipeService.getById(recipe.getId());
        if(recipeInDB == null){
            return Result.fail("没有此菜谱！");
        }
        //2.修改菜谱
        recipeService.updateById(recipe);
        //3.返回
        return Result.ok(recipe);
    }

    /**
     * 菜谱删除接口
     * @param id 菜谱id
     * @return
     */
    @PostMapping("/delete")
    public Result<Recipe> delete(@RequestParam Long id){
        //1.根据id删除
        recipeService.removeById(id);
        //2.返回删除结果
        return Result.ok("删除菜谱成功！");
    }

    /**
     * 根据id查找菜谱接口
     * @param id
     * @return
     */
    @PostMapping("/search/id")
    public Result<Recipe> searchById(@RequestParam int id){
        //1.根据id查找菜谱
        Recipe recipe = recipeService.getById(id);
        //2.返回
        return Result.ok(recipe);
    }

    /**
     * 根据名字查找菜谱接口
     * @param name
     * @return
     */
    @PostMapping("/search/name")
    public Result<Recipe> searchByName(@RequestParam String name){
        //1.根据名字搜索匹配菜谱
        QueryWrapper<Recipe> wrapper = new QueryWrapper<>();
        wrapper
                .eq("name",name);
        Recipe recipe = recipeService.getOne(wrapper);
        //2.返回
        return Result.ok(recipe);
    }

    @PostMapping("/search/uid")
    public Result<Page<Recipe>> searchByUid(@RequestParam Long uId,@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        //1.根据名字搜索匹配菜谱
        LambdaQueryWrapper<Recipe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Recipe::getUId,uId);
        //2.创建分页条件
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        Page<Recipe> recipePage = recipeService.page(page, wrapper);
        //2.返回
        return Result.ok(recipePage);
    }

    /**
     * 分页查询菜谱接口
     * @param pageNum 菜谱页数
     * @param pageSize 展示多少个菜谱
     * @return
     */
    @PostMapping("/search/page")
    public Result<Page<Recipe>> page(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        //1.创建分页条件
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        //2.分页查询
        Page<Recipe> recipePage = recipeService.page(page);
        //3.返回结果
        return Result.ok(recipePage);
    }
}
