package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Dto.SetmealDto;
import com.example.domain.Dish;
import com.example.domain.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
    
    SetmealDto getByIdWithDto(long id);
    
    void updateWithDish(SetmealDto setmealDto);
    
    void deleteWithDto(List<Long> ids);
    
    void stopsale(List<Long> params);
    
    void startsale(List<Long> params);
}
