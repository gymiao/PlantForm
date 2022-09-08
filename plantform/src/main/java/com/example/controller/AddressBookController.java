package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.domain.AddressBook;
import com.example.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// https://github.com/miao001-1/Take_Out.git
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }
    
    @PostMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        // 先用luw将userId用户的地址给位非默认，在设置默认
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();
        luw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        luw.set(AddressBook::getIsDefault, 0);
        addressBookService.update(luw);

        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    
    @PutMapping("/default")
    public R<AddressBook> updateDefault(@RequestBody AddressBook addressBook){
        // 先用luw将userId用户的地址给位非默认，在设置默认
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();
        luw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        luw.set(AddressBook::getIsDefault, 0);
        addressBookService.update(luw);
        
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        // 先用luw将userId用户的地址给位非默认，在设置默认
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lqw.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(lqw);
        return R.success(addressBook);
    }
    
    
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(addressBook.getUserId()!=null,AddressBook::getUserId,addressBook.getUserId());
        lqw.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBookList = addressBookService.list(lqw);
        return R.success(addressBookList);
    }
}










