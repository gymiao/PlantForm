package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dto.DishDto;
import com.example.common.CustomException;
import com.example.domain.Category;
import com.example.domain.Dish;
import com.example.domain.DishFlavor;
import com.example.mapper.CategoryMapper;
import com.example.mapper.DishMapper;
import com.example.service.CategoryService;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    
    @Autowired
    private DishFlavorService dishFlavorService;
    
    
    @Override
    @Transactional
    //事务管理,需在启动类上开启
    public void saveWithFlavor(DishDto dishDto) {
        //dishDto为dish字类
        this.save(dishDto);
        
        Long dishId = dishDto.getId();
    
        // 设置id
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        // 保存
        dishFlavorService.saveBatch(flavors);
    }
    
    @Override
    public DishDto getByIdWithFlavor(Long id){
        
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(lqw);
        dishDto.setFlavors(list);
        return dishDto;
    }
    
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
    
    @Override
    public void deleteWithFlavor(Long[] ids) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.in(Dish::getId,Arrays.asList(ids));
        lqw.eq(Dish::getStatus,1);
        int count = this.count(lqw);
        if (count > 0) {
            throw new CustomException("不能删除在售菜品");
        }
        this.removeByIds(Arrays.asList(ids));
        dishFlavorService.removeByIds(Arrays.asList(ids));
        
    }
}













