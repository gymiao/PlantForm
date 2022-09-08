package com.example.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.DishFlavor;
import com.example.domain.Setmeal;
import com.example.mapper.DishFlavorMapper;
import com.example.mapper.SetmealMapper;
import com.example.service.DishFlavorService;
import com.example.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
