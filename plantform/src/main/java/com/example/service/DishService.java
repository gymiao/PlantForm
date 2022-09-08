package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Dto.DishDto;
import com.example.domain.Category;
import com.example.domain.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByIdWithFlavor(Long id);
    
    void updateWithFlavor(DishDto dishDto);
    
    void deleteWithFlavor(Long[] ids);
}
