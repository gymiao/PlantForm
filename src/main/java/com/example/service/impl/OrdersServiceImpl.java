package com.example.service.impl;

import com.example.domain.Orders;
import com.example.dao.OrdersDao;
import com.example.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements IOrdersService {

}
