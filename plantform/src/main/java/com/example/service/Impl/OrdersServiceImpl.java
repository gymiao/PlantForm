package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.common.CustomException;
import com.example.domain.*;
import com.example.mapper.AddressBookMapper;
import com.example.mapper.OrdersMapper;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AddressBookService addressBookService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    @Override
    public void submit(Orders orders) {
        Long userId = BaseContext.getCurrentId();
    
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lqw);
        
        if (shoppingCartList == null || shoppingCartList.size() == 0){
            throw new CustomException("购物车为空不能下单！");
        }
        
        // 查用户
        User user = userService.getById(userId);
        
        // 查地址
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null ){
            throw new CustomException("地址不能为空！");
        }
        
        // 保存
        orders.setUserId(userId);
        Long orderId = IdWorker.getId();
        // 原子线程，多线程不会出错
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        orders.setNumber(String.valueOf(orderId));
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get())); //总金额
        orders.setUserName(String.valueOf(orderId));
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "":addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "":addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" :addressBook.getCityName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        
        this.save(orders);
        
        orderDetailService.saveBatch(orderDetails);
        
        shoppingCartService.remove(lqw);
    }
}











