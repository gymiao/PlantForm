package com.example.controller;

import com.example.common.BaseContext;
import com.example.common.R;
import com.example.domain.Orders;
import com.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("提交成功");
    }
}
