package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Dto.DishDto;
import com.example.common.R;
import com.example.domain.Category;
import com.example.domain.Dish;
import com.example.domain.DishFlavor;
import com.example.service.CategoryService;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    
    @Autowired
    private DishFlavorService dishFlavorService;
    
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    public R<String>  save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
    
    @PutMapping
    public R<String>  update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("更新菜品成功");
    }
    
    @DeleteMapping
    public R<String>  delete(Long[] ids){
        dishService.deleteWithFlavor(ids);
        return R.success("菜品删除成功");
    }
    
    @PostMapping("/status/0")
    public R<String> haltSale(Long[] ids){
        List<Dish> dishList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Dish dish = new Dish();
            dish.setId(ids[i]);
            dish.setStatus(0);
            dishList.add(dish);
        }
        dishService.updateBatchById(dishList);
        return R.success("已停售该商品");
    }
    
    @PostMapping("/status/1")
    public R<String> startSale(Long[] ids){
        List<Dish> dishList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Dish dish = new Dish();
            dish.setId(ids[i]);
            dish.setStatus(1);
            dishList.add(dish);
        }
        dishService.updateBatchById(dishList);
        return R.success("已起售该商品");
    }
    
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        lqw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,lqw);
    
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> list = pageInfo.getRecords();
        List<DishDto> dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
        
    }
    
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }
    
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Dish::getStatus,1);
//        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId, dish.getCategoryId());
//        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> dishList = dishService.list(lqw);
//        return R.success(dishList);
//    }
    
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getStatus,1);
        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId, dish.getCategoryId());
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(lqw);
    
        List<DishDto> dishDtoList = dishList.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
    
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            
            return dishDto;
        }).collect(Collectors.toList());
    
//        List<DishDto> dishDtoList = new ArrayList<>();
//        BeanUtils.copyProperties(dishList,dishDtoList);
//        dishDtoList = dishDtoList.stream().map((item) -> {
//            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(DishFlavor::getDishId,item.getId());
//            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
//            item.setFlavors(dishFlavors);
//            return item;
//        }).collect(Collectors.toList());
    
        return R.success(dishDtoList);
    }
    
    
    
}
























