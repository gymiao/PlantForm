package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomException;
import com.example.domain.Category;
import com.example.domain.Dish;
import com.example.domain.Setmeal;
import com.example.mapper.CategoryMapper;
import com.example.service.CategoryService;
import com.example.service.DishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    
    @Autowired
    private DishService dishService;
    
    @Autowired
    private SetmealService setmealService;
    /*
    aop思想
    根据id判断是否可以删除
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dlqw = new LambdaQueryWrapper<>();
        dlqw.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dlqw);
        if (count1 > 0){
            throw new CustomException("该分类下关联菜品，不能删除");
        }
    
        LambdaQueryWrapper<Setmeal> slqw = new LambdaQueryWrapper<>();
        slqw.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(slqw);
        if (count2 > 0){
            throw new CustomException("该分类下关联套餐，不能删除");
        }
        super.removeById(id);
    }
}











