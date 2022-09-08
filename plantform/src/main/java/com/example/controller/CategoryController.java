package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.domain.Category;
import com.example.domain.Employee;
import com.example.service.CategoryService;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    
    /*
新增员工
 */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        // 构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,lqw);

        return R.success(pageInfo);
    }
    
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> lqw =new LambdaQueryWrapper<>();
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
//
//    /*
//    update
//     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }
//
    @DeleteMapping
    public R<String> delete(Long id){
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }
}


















