package com.example.service.impl;

import com.example.domain.Employee;
import com.example.dao.EmployeeDao;
import com.example.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author miaogy
 * @since 2022-08-30
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements IEmployeeService {

}
