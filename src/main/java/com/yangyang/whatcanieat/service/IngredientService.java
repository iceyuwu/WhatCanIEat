package com.yangyang.whatcanieat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.whatcanieat.entity.Ingredient;
import com.yangyang.whatcanieat.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

@Service
public class IngredientService extends ServiceImpl<IngredientMapper , Ingredient> {
}
