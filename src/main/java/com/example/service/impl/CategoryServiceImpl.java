package com.example.service.impl;

import com.example.domain.Category;
import com.example.dao.CategoryDao;
import com.example.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements ICategoryService {

}
