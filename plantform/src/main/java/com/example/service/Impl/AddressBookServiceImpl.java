package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomException;
import com.example.domain.AddressBook;
import com.example.domain.Category;
import com.example.domain.Dish;
import com.example.domain.Setmeal;
import com.example.mapper.AddressBookMapper;
import com.example.mapper.CategoryMapper;
import com.example.service.AddressBookService;
import com.example.service.CategoryService;
import com.example.service.DishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}











