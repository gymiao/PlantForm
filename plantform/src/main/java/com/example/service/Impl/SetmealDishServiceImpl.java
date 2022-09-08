package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Setmeal;
import com.example.domain.SetmealDish;
import com.example.mapper.SetmealDishMapper;
import com.example.mapper.SetmealMapper;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
