package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.mapper.RecipeMapper;
import org.springframework.stereotype.Service;

@Service
public class RecipeService extends ServiceImpl<RecipeMapper, Recipe> {
}
