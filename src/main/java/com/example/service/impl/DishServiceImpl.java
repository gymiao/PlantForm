package com.example.service.impl;

import com.example.domain.Dish;
import com.example.dao.DishDao;
import com.example.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements IDishService {

}
