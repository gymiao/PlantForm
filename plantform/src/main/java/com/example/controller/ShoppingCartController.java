package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.domain.ShoppingCart;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lqw);
        return R.success(shoppingCartList);
    }
    
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getCurrentId());
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        
        if(dishId != null){
            lqw.eq(ShoppingCart::getDishId,dishId);
        }else {
            lqw.eq(ShoppingCart::getSetmealId,setmealId);
        }
        // 判断是否已在购物车中
        ShoppingCart one = shoppingCartService.getOne(lqw);
        if (one == null){
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            
            one = shoppingCart;
        }else {
            Integer amount = one.getNumber() + 1;
            one.setNumber(amount);
            shoppingCartService.updateById(one);
        }
        return R.success(one);
    }
    
    @DeleteMapping("/clean")
    public R<String > clean(){
        
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(lqw);
        return R.success("清除成功!");
    }
}


































