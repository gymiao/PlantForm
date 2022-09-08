package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dto.SetmealDto;
import com.example.common.CustomException;
import com.example.domain.Category;
import com.example.domain.Setmeal;
import com.example.domain.SetmealDish;
import com.example.mapper.CategoryMapper;
import com.example.mapper.SetmealMapper;
import com.example.service.CategoryService;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
    
    @Override
    public SetmealDto getByIdWithDto(long id) {
        Setmeal setmeal = this.getById(id);
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, id);
        lqw.orderByDesc(SetmealDish::getUpdateTime);
        List<SetmealDish> list = setmealDishService.list(lqw);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }
    
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);
        long setmealId = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(lqw);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList = setmealDishList.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishList);
    }
    
    @Transactional
    @Override
    public void deleteWithDto(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(Setmeal::getId,ids);
        lqw1.eq(Setmeal::getStatus, 1);
        
        // 看是否有在售的
        int count = this.count(lqw1);
        
        if (count > 0){
            throw new CustomException("不能删除在售的商品");
        }
        LambdaQueryWrapper<SetmealDish> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lqw2);
        
        this.removeByIds(ids);
    }
    
    @Override
    public void stopsale(List<Long> params) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(0);
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,params);
        this.update(setmeal,lqw);
    }
    
    @Override
    public void startsale(List<Long> params) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(1);
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,params);
        this.update(setmeal,lqw);
    }
}
