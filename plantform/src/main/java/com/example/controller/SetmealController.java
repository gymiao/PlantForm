package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Dto.SetmealDto;
import com.example.common.R;
import com.example.domain.Category;
import com.example.domain.Setmeal;
import com.example.service.CategoryService;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    
    @Autowired
    private SetmealDishService setmealDishService;
    
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    // json封装要用@RB
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }
    
    @PutMapping
    // json封装要用@RB
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("套餐更新成功");
    }
    
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealService.page(pageInfo,lqw);
    
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        /*
        思路，用stream流处理数据，将返回数据在进行封装
         */
        List<SetmealDto> setmealDtoList = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            //得到种类ID
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
    
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }
    
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable long id){
        SetmealDto setmealDto = setmealService.getByIdWithDto(id);
        return R.success(setmealDto);
    }
    
    @DeleteMapping
    public R<String> delete(Long[] ids){
        List<Long> params = new ArrayList<>(Arrays.asList(ids));
        setmealService.deleteWithDto(params);
        
        return R.success("删除成功");
    }
    
    @PostMapping("/status/0")
    public R<String> stopsale(Long[] ids){
        List<Long> params = new ArrayList<>(Arrays.asList(ids));
        setmealService.stopsale(params);
        
        return R.success("套餐停售成功");
    }
    
    @PostMapping("/status/1")
    public R<String> startsale(Long[] ids){
        List<Long> params = new ArrayList<>(Arrays.asList(ids));
        setmealService.startsale(params);
        
        return R.success("套餐起售成功");
    }
    
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        lqw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmealList = setmealService.list(lqw);
        return R.success(setmealList);
    }
    
}






















