package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Category;
import com.example.domain.Employee;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
