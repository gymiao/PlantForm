package com.example.service.impl;

import com.example.domain.Address_book;
import com.example.dao.Address_bookDao;
import com.example.service.IAddress_bookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Service
public class Address_bookServiceImpl extends ServiceImpl<Address_bookDao, Address_book> implements IAddress_bookService {

}
