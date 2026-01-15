package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.RecipeIngredient;
import com.yangyang.whatcanieat.mapper.RecipeIngredientMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService extends ServiceImpl<RecipeIngredientMapper, RecipeIngredient> {
}
