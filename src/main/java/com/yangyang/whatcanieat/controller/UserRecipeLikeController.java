package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.entity.UserRecipeLike;
import com.yangyang.whatcanieat.service.RecipeService;
import com.yangyang.whatcanieat.service.UserRecipeLikeService;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/user/like")
public class UserRecipeLikeController {

    @Resource
    private UserService userService;
    @Resource
    private RecipeService recipeService;
    @Resource
    private UserRecipeLikeService userRecipeLikeService;


    @PostMapping("/change")
    public Result<Boolean> like(@RequestParam Long recId){
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户未登录！");
        }
        //查询菜谱是否存在
        Recipe recipe = recipeService.getById(recId);
        if (recipe == null){
            return Result.fail("菜谱不存在");
        }
        //查询是否存在收藏
        LambdaQueryWrapper<UserRecipeLike> wrapper = new LambdaQueryWrapper();
        wrapper
                .eq(UserRecipeLike::getUId ,user.getId())
                .eq(UserRecipeLike::getRecId, recId);

        UserRecipeLike userRecipeLike = userRecipeLikeService.getOne(wrapper);
        //不存在收藏，创建收藏
        if (userRecipeLike == null){
            UserRecipeLike newLike = new UserRecipeLike();
            newLike.setUId(user.getId());
            newLike.setRecId(recId);
            newLike.setCreateTime(LocalDateTime.now());
            userRecipeLikeService.save(newLike);
            return Result.ok(true,"已收藏");
        }
        //存在收藏，删除收藏
        else {
            userRecipeLikeService.removeById(userRecipeLike);
            return Result.ok(false,"已取消收藏");
        }

    }

    @PostMapping("/check")
    public Result<Boolean> checkLike(@RequestParam Long recId){
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户未登录！");
        }
        //查询菜谱是否存在
        Recipe recipe = recipeService.getById(recId);
        if (recipe == null){
            return Result.fail("菜谱不存在");
        }
        //查询是否存在收藏
        LambdaQueryWrapper<UserRecipeLike> wrapper = new LambdaQueryWrapper();
        wrapper
                .eq(UserRecipeLike::getUId ,user.getId())
                .eq(UserRecipeLike::getRecId, recId);

        UserRecipeLike userRecipeLike = userRecipeLikeService.getOne(wrapper);
        if (userRecipeLike == null){
            return Result.ok(false,"未收藏");
        }
        return Result.ok(true,"已收藏");
    }

    @PostMapping("/list")
    public Result<Page<Recipe>> getLikeList(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户未登录！");
        }
        //查询用户所有收藏
        LambdaQueryWrapper<UserRecipeLike> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(UserRecipeLike::getUId,user.getId())
                .orderByAsc(UserRecipeLike::getCreateTime);
        List<UserRecipeLike> userRecipeLikeList = userRecipeLikeService.list(wrapper);
        if (userRecipeLikeList == null || userRecipeLikeList.isEmpty() || userRecipeLikeList.size() == 0){
            return Result.ok(new Page<>(pageNum, pageSize),"喜爱菜谱列表为空");
        }
        //获取菜谱id列表并查询所有菜谱
        List<Long> recIdList = new ArrayList<>();
        for (UserRecipeLike userRecipeLike : userRecipeLikeList) {
            recIdList.add(userRecipeLike.getRecId());
        }
        LambdaQueryWrapper<Recipe> recipeWrapper = new LambdaQueryWrapper<>();
        recipeWrapper.in(Recipe::getId,recIdList);
        //2.创建分页条件
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        Page<Recipe> recipePage = recipeService.page(page, recipeWrapper);

        return Result.ok(recipePage,"查询喜爱菜谱列表成功");
    }

    /**
     * 推荐菜谱接口
     * 查询和当前用户收藏了相同菜谱的其他用户收藏的其他菜谱
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/recommend")
    public Result<Page<Recipe>> getRecommendList(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户未登录！");
        }
        //查询当前用户所有收藏的菜谱id列表
        LambdaQueryWrapper<UserRecipeLike> currentUserLikeWrapper = new LambdaQueryWrapper<>();
        currentUserLikeWrapper
                .eq(UserRecipeLike::getUId,user.getId())
                .orderByAsc(UserRecipeLike::getCreateTime);
        List<UserRecipeLike> userRecipeLikeList = userRecipeLikeService.list(currentUserLikeWrapper);
        if (userRecipeLikeList == null || userRecipeLikeList.isEmpty() || userRecipeLikeList.size() == 0){
            return Result.ok(new Page<>(pageNum, pageSize),"喜爱菜谱列表为空");
        }
        List<Long> currentUserLikeRecIdList = new ArrayList<>();
        for (UserRecipeLike userRecipeLike : userRecipeLikeList) {
            currentUserLikeRecIdList.add(userRecipeLike.getRecId());
        }
        //查询喜爱当前用户收藏的菜谱的其他用户的id集合
        LambdaQueryWrapper<UserRecipeLike> otherUserWrapper = new LambdaQueryWrapper<>();
        otherUserWrapper.in(UserRecipeLike::getRecId,currentUserLikeRecIdList);
        List<UserRecipeLike> otherUserRecipeLikeList = userRecipeLikeService.list(otherUserWrapper);
        //将其他相同喜好的用户id去重保存
        HashSet<Long> otherUserIdSet = new HashSet<>();
        for (UserRecipeLike otherUserRecipeLike : otherUserRecipeLikeList) {
            otherUserIdSet.add(otherUserRecipeLike.getUId());
        }
        //查询其他相同喜好的用户的所有喜好菜谱
        LambdaQueryWrapper<UserRecipeLike> otherUserLikesWrapper = new LambdaQueryWrapper<>();
        otherUserLikesWrapper.in(UserRecipeLike::getUId,otherUserIdSet);
        List<UserRecipeLike> otherUserRecipeLikesList = userRecipeLikeService.list(otherUserLikesWrapper);
        //将所有其他用户喜好的菜谱id去重保存，作为推荐菜谱id集合
        HashSet<Long> recommendRecipeIdSet = new HashSet<>();
        for (UserRecipeLike userRecipeLike : otherUserRecipeLikesList) {
            recommendRecipeIdSet.add(userRecipeLike.getRecId());
        }
        //推荐菜谱id集合去除当前用户已经收藏的菜谱id
//        for (Long removeRecId : currentUserLikeRecIdList) {
//            recommendRecipeIdSet.remove(removeRecId);
//        }
        //从中选取五个返回
        List<Long> recommendRecipeIdList = new ArrayList<>(recommendRecipeIdSet);
        Collections.shuffle(recommendRecipeIdList); // 打乱顺序
        recommendRecipeIdList.subList(0, Math.min(pageSize, recommendRecipeIdList.size()));
        LambdaQueryWrapper<Recipe> recipeWrapper = new LambdaQueryWrapper<>();
        recipeWrapper
                .in(Recipe::getId,recommendRecipeIdList)
                .last("ORDER BY RAND()");
        //2.创建分页条件
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        Page<Recipe> recipePage = recipeService.page(page, recipeWrapper);

        return Result.ok(recipePage,"获取推荐菜谱列表成功");
    }

    @PostMapping("/random")
    public Result<Page<Recipe>> getrandomList(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        // 1. 只查ID（减少数据量）
        List<Long> ids = recipeService.listObjs(
                new QueryWrapper<Recipe>().select("id"),
                obj -> (Long) obj
        );
        // 2. 打乱
        Collections.shuffle(ids);
        // 3. 取前X个
        List<Long> randomIds = ids.subList(0, Math.min(pageSize, ids.size()));
        // 4. 根据ID查询
        List<Recipe> list = recipeService.listByIds(randomIds);
        Page<Recipe> page = new Page<>(pageNum, pageSize);
        page.setRecords(list);      // 当前页数据
        page.setTotal(list.size());
        return Result.ok(page,"随机获取菜谱");
    }
}
