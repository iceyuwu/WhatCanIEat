package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangyang.whatcanieat.entity.Comment;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.entity.User;
import com.yangyang.whatcanieat.service.CommentService;
import com.yangyang.whatcanieat.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    // ================== 新增评论 ==================
    @PostMapping("/add")
    public Result<Comment> add(@RequestBody Comment comment) {
//0.获取用户信息
        // 通过SpringSecurity获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("account",userAccount);
        User user = userService.getOne(userWrapper);
        if (user == null){
            return Result.fail("用户未登录");
        }

        comment.setUId(user.getId());
        comment.setUName(user.getName());
        comment.setCreateTime(LocalDateTime.now());

        commentService.save(comment);
        return Result.ok(comment);
    }

    // ================== 根据菜谱查询评论 ==================
    @PostMapping("/list")
    public Result<List<Comment>> list(@RequestBody Map<String, Long> param) {

        Long recId = param.get("recId");

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("rec_id", recId)
                .orderByDesc("create_time");

        return Result.ok(commentService.list(wrapper));
    }

    // ================== 删除评论 ==================
    @PostMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        commentService.removeById(id);
        return Result.ok("删除成功");
    }

    // ================== 点赞 ==================
    @PostMapping("/like/{id}")
    public Result<Comment> like(@PathVariable Long id) {
        Comment comment = commentService.getById(id);
        if (comment == null) return Result.fail("评论不存在");

        comment.setLikeCount(comment.getLikeCount() + 1);
        commentService.updateById(comment);
        return Result.ok(comment);
    }
}