package com.yangyang.whatcanieat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.service.RecipeIngredientService;
import com.yangyang.whatcanieat.service.RecipeService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
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

    @GetMapping(value = "/chat/recipe",produces = "text/html;charset=utf-8")
    public Flux<String> recipeChat(@RequestParam String prompt,@RequestParam Long id) {

        //查询菜谱信息
        Recipe recipe = recipeService.getById(id);
        return chatClient.prompt()
                .user("根据菜谱信息回答用户的提问。" +
                        "菜名:" + recipe.getName() + ";" +
                        "菜谱描述:" + recipe.getText() + ";" +
                        "以下是用户提问："+ prompt
                )
                .stream()
                .content();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/chat/flux",produces = "text/html;charset=utf-8")
    public Flux<String> chatFlux(@RequestParam String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    @GetMapping("/chat/blocking")
    public String chatBlocking(@RequestParam String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
