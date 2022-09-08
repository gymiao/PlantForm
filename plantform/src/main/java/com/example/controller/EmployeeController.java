package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.domain.Employee;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    /*
    登录
     */
    @PostMapping("/login")
    // 请求体作为参数,并将id存在本地session
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // md5解密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        
        //查数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        
        if (emp == null) {
            return R.error("没有该用户");
        }
        
        // 比对密码
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        
        if (emp.getStatus() == 0){
            return R.error("该用户被禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    
    /*
新增员工
 */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
        long empId = (long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("添加成功");
    }
    
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        // 构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        
        // 构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lqw.orderByDesc(Employee::getUpdateTime);
        
        employeeService.page(pageInfo,lqw);
        
        return R.success(pageInfo);
    }
    
    /*
    update
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("修改成功");
    }
    
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("找不到该用户");
    }
}


















