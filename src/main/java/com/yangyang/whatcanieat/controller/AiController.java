package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.service.RecipeIngredientService;
import com.yangyang.whatcanieat.service.RecipeService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;

    @Resource
    private RecipeService recipeService;
    @Resource
    private RecipeIngredientService recipeIngredientService;

    @GetMapping(value = "/chat/recipe", produces = "text/html;charset=utf-8")
//    @GetMapping(value = "/chat/recipe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String recipeChat(@RequestParam String prompt, @RequestParam Long id) {
        // 查询菜谱
        Recipe recipe = recipeService.getById(id);

        // 查询原料
        LambdaQueryWrapper<RecipeIngredient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RecipeIngredient::getRecId, id);
        List<RecipeIngredient> list = recipeIngredientService.list(wrapper);

        // 拼接原料
        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient item : list) {
            sb.append(item.getName())
                    .append(" ")
                    .append(item.getNumber())
                    .append(getUnitStr(item.getUnit()))
                    .append("（")
                    .append(getTypeStr(item.getType()))
                    .append("）\n");
        }

        // 去掉最后换行（可选）
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        String ingredients = sb.toString();

        return chatClient.prompt()
                .system("""
                        你是一个专业的菜谱助手，名字叫“米团”，请遵守以下规则：
                        1. 回答必须围绕提供的菜谱
                        2. 回答简洁清晰
                        3. 使用中文
                        4. 以“米团”自称，可以以“米团觉得”或“米团认为”开始一段回复
                        5. 你的形象是一个热情贴心的女性，说话风格比较可爱
                        6. 喜欢甜食，讨厌高热量食物
                        """)
                .user("""
                        【菜谱信息】
                        菜名：%s
                        描述：%s
                        原料：
                        %s
                        
                        【用户问题】
                        %s
                        """.formatted(
                        recipe.getName(),
                        recipe.getText(),
                        ingredients,
                        prompt
                ))
                .call()
                .content();
    }

    private String getUnitStr(Integer unit) {
        if (unit == null) return "";
        switch (unit) {
            case 1:
                return "克";
            case 2:
                return "个";
            case 3:
                return "毫升";
            default:
                return "";
        }
    }

    private String getTypeStr(Integer type) {
        if (type == null) return "";
        switch (type) {
            case 1:
                return "关键";
            case 2:
                return "可选";
            case 3:
                return "辅料";
            default:
                return "";
        }
    }
}
