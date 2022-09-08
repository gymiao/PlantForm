package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.AddressBook;
import com.example.domain.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
